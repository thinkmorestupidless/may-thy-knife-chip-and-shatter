package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.exchange.entities.MarketFilter;
import cc.xuloo.betfair.client.exchange.ExchangeApi;

public class BetfairExchangeActor extends AbstractActor {

    public static Props props(ExchangeApi api) {
        return Props.create(BetfairExchangeActor.class, () -> new BetfairExchangeActor(api));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ExchangeApi api;

    private ActorRef listener;

    public BetfairExchangeActor(ExchangeApi api) {
        this.api = api;
    }

    @Override
    public Receive createReceive() {
        return disconnected();
    }

    public Receive disconnected() {
        return receiveBuilder()
                .match(ExchangeCommand.Connect.class, cmd -> {
                    getContext().become(authenticating());
                    api.login()
                            .thenAccept(response -> {
                                getContext().become(authenticated());

                                BetfairSession session = BetfairSession.loggedIn(response.getSessionToken(), cmd.getApplicationKey());
                                cmd.getListener().tell(new ExchangeEvent.Connected(session), getSelf());
                            });
                })
                .matchAny(o -> log.info("i'm in state 'disconnected' and i don't know what to do with {}", o))
                .build();
    }

    public Receive authenticating() {
        return receiveBuilder()
                .matchAny(o -> log.info("i'm in state 'authenticating' and i don't know what to do with {}", o))
                .build();
    }

    public Receive authenticated() {
        return receiveBuilder()
                .match(ExchangeProtocol.Command.class, cmd -> {
                    BetfairSession session = cmd.getSession();
                    ExchangeProtocol protocol = cmd.getCommand();

                    if (protocol instanceof ExchangeProtocol.ListEvents) {
                        log.info("listing events -> {} {}", cmd.getListener(), getSender());

                        MarketFilter filter = ((ExchangeProtocol.ListEvents) protocol).getFilter();

                        api.listEvents(session, filter)
                                .exceptionally(t -> {
                                    log.warning("problem listing events: {}", t);
                                    return null;
                                }).thenAccept(response -> {
                            if (response.getError() != null) {
                                log.warning("error listing events -> ", response.getError());
                            }

                            cmd.getListener().tell(response, getSelf());
                        });

                    } else if (protocol instanceof ExchangeProtocol.ListMarketCatalogues) {
                        log.debug("listing market catalogues");

                        ExchangeProtocol.ListMarketCatalogues lmc = (ExchangeProtocol.ListMarketCatalogues) cmd.getCommand();

                        api.listMarketCatalogue(cmd.getSession(), lmc.getFilter(), lmc.getMarketProjections(), lmc.getSort(), lmc.getMaxResults())
                                .exceptionally(t -> {
                                    log.error("problem listing market catalogues -> {}", t);
                                    return null;
                                })
                                .thenAccept(response -> {
                                    if (response.getError() != null) {
                                        log.warning("error listing market catalogues -> ", response.getError());
                                    }

                                    cmd.getListener().tell(response, getSelf());
                                });
                    }
                })
                .matchAny(o -> log.info("i'm in state 'authenticated' and i don't know what to do with {}", o))
                .build();
    }
}
