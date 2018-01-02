package cc.xuloo.betfair.impl;

import akka.Done;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.exchange.containers.ListMarketCatalogueContainer;
import cc.xuloo.betfair.client.exchange.entities.Event;
import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;
import cc.xuloo.betfair.client.exchange.entities.MarketFilter;
import cc.xuloo.betfair.client.exchange.enums.MarketProjection;
import cc.xuloo.betfair.client.exchange.enums.MarketSort;
import cc.xuloo.betfair.client.ExchangeProtocol;
import cc.xuloo.utils.CompletionStageUtils;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class EventMonitor extends AbstractActor {

    public static Props props(ActorRef betfair, PersistentEntityRegistry registry) {
        return Props.create(EventMonitor.class, betfair, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef betfair;

    private final PersistentEntityRegistry registry;

    private List<MarketCatalogue> catalogues;

    private ActorRef listener;

    public EventMonitor(ActorRef betfair, PersistentEntityRegistry registry) {
        this.betfair = betfair;
        this.registry = registry;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BetfairServiceProtocol.MonitorEvent.class, this::monitorEvent)
                .build();
    }

    public Receive waitingForCatalogue(String eventId) {
        return receiveBuilder()
                .match(ListMarketCatalogueContainer.class, container -> {
                    if (container.getError() != null) {
                        log.warning("error listing market catalogues -> {}", container.getError());
                    } else {
                        catalogues = container.getResult();

//                        List<CompletionStage<Done>> stages = catalogues.stream()
//                                .map(catalogue -> subscribeToMarket(catalogue, eventId))
//                                .collect(Collectors.toList());

//                        List<MarketCatalogue> sorted = catalogues.stream().sorted((a, b) -> b.getTotalMatched().compareTo(a.getTotalMatched())).collect(Collectors.toList());

                        CompletionStage<Done> operation = catalogues.stream()
                                .sorted((a, b) -> b.getTotalMatched().compareTo(a.getTotalMatched()))
                                .map(c -> subscribeToMarket(c, eventId))
                                .findFirst().get();

                        List<CompletionStage<Done>> stages = Lists.newArrayList(operation);

                                CompletionStageUtils.doAll(stages)
                                .exceptionally(throwable -> {
                                    log.warning("failed to complete all markets -> {}", throwable);
                                    return Done.getInstance();
                                }).thenAccept(done -> {
                                    listener.tell(new BetfairServiceProtocol.EventMonitored(catalogues), getSelf());
                                });
                    }
                })
                .build();
    }

    public void monitorEvent(BetfairServiceProtocol.MonitorEvent cmd) {
        log.info("monitoring event -> {}", getSender());

        listener = getSender();
        Event event = cmd.getEvent();

        registry.refFor(BetfairEntity.class, event.getId())
                .ask(new BetfairCommand.AddFixture(event))
                .thenAccept(done -> {
                    MarketFilter filter = MarketFilter.builder()
                            .eventId(cmd.getEvent().getId())
                            .build();

                    ExchangeProtocol protocol = ExchangeProtocol.ListMarketCatalogues.builder()
                            .filter(filter)
                            .marketProjection(MarketProjection.RUNNER_DESCRIPTION)
                            .sort(MarketSort.MAXIMUM_TRADED)
                            .maxResults(100)
                            .build();

                    betfair.tell(protocol, getSelf());

                    getContext().become(waitingForCatalogue(cmd.getEvent().getId()));
                });
    }

    public CompletionStage<Done> subscribeToMarket(MarketCatalogue catalogue, String eventId) {
        return registry.refFor(BetfairEntity.class, eventId)
                       .ask(new BetfairCommand.AddMarketCatalogue(catalogue));
    }
}
