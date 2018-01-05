package cc.xuloo.betfair;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.client.BetfairConnection;
import cc.xuloo.betfair.client.settings.BetfairSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BetfairConfiguration {

    private final ActorSystem system;

    private final Config config;

    private final ObjectMapper mapper;

    private final BetfairConnection connection;

    private final ActorRef streamMessageHandler;

    public BetfairSettings getSettings() {
        return BetfairSettings.fromConfig(getConfig());
    }
}
