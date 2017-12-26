package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.inject.Named;

public class BetfairMonitor {

    private final static Logger log = LoggerFactory.getLogger(BetfairMonitor.class);

    @Inject
    public BetfairMonitor(Config config, @Named("betfair-worker") ActorRef worker) {
        if (config.getString("mtkcas.betfair.monitor").equals("auto-start")) {
            log.info("auto-starting betfair monitor");

            start(worker);
        }
    }

    public void start(ActorRef worker) {
        worker.tell(new BetfairProtocol.Start(), ActorRef.noSender());
    }
}
