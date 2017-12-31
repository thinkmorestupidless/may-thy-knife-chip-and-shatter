package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.BetfairConnection;
import cc.xuloo.betfair.client.ExchangeApi;
import cc.xuloo.betfair.client.actors.*;
import cc.xuloo.betfair.client.asynchttp.AsyncHttpBetfairConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class BetfairMonitor {

    private final static Logger log = LoggerFactory.getLogger(BetfairMonitor.class);

    @Inject
    public BetfairMonitor(ActorSystem system, Config config, @Named("betfair-client") ActorRef betfair, PersistentEntityRegistry registry) {
        ActorRef worker = system.actorOf(BetfairWorker.props(config, betfair, registry), "betfair-worker");
        worker.tell(new BetfairProtocol.Start(), ActorRef.noSender());
    }
}
