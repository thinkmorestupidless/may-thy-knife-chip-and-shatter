package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetfairMonitor {

    private final static Logger log = LoggerFactory.getLogger(BetfairMonitor.class);

    @Inject
    public BetfairMonitor(ActorSystem system, Config config, @Named("betfair-client") ActorRef betfair, PersistentEntityRegistry registry) {
        ActorRef worker = system.actorOf(BetfairWorker.props(config, betfair, registry), "betfair-worker");
        worker.tell(new BetfairServiceProtocol.Start(), ActorRef.noSender());
    }
}
