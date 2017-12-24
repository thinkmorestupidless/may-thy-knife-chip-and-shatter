package cc.xuloo.fixture.impl;

import akka.actor.ActorSystem;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetfairWorker {

    private final static Logger log = LoggerFactory.getLogger(BetfairWorker.class);

    private final ActorSystem system;

    @Inject
    public BetfairWorker(ActorSystem system) {
        this.system = system;

        log.info("creating betfair worker -> {}", this.system);
    }
}
