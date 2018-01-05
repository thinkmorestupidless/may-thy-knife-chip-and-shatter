package cc.xuloo.betfair.client.settings;

import com.typesafe.config.Config;
import lombok.Value;

@Value
public class BetfairSettings {

    public static BetfairSettings fromConfig(Config config) {
        return new BetfairSettings(
                StreamSettings.fromConfig(config),
                ClientSettings.fromConfig(config));
    }

    private final StreamSettings stream;

    private final ClientSettings client;
}
