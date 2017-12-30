package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.BetfairSession;
import cc.xuloo.betfair.client.ExchangeApi;

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
        return receiveBuilder()
                .match(ExchangeProtocol.Login.class, this::handleLogin)
                .match(ExchangeProtocol.Command.class, this::handleCommand)
                .build();
    }

    public void handleLogin(ExchangeProtocol.Login cmd) {
        log.info("logging in to Exchange");

        listener = getSender();

        api.login().thenAccept(response -> listener.tell(response, getSelf()));
    }

    public void handleCommand(ExchangeProtocol.Command cmd) {
        BetfairSession session = cmd.getSession();
        ExchangeProtocol protocol = cmd.getCommand();

        if (protocol instanceof ExchangeProtocol.ListEvents) {
            log.info("listing events");

            MarketFilter filter = ((ExchangeProtocol.ListEvents) protocol).getFilter();

            api.listEvents(session, filter).thenAccept(response ->
                getSender().tell(response, getSelf()));
        }
    }
}
