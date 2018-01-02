package cc.xuloo.betfair.client.settings;

import com.typesafe.config.Config;
import lombok.Value;

@Value
public class ClientSettings {

    public enum StartType {
        AUTO, MANUAL
    }

    public static ClientSettings fromConfig(Config config) {
        return new ClientSettings(
                BetfairCredentials.fromConfig(config),
                StartType.valueOf(config.getString("betfair.client.start-type").toUpperCase()));
    }

    private final BetfairCredentials credentials;

    private final StartType startType;

    public boolean autoStart() {
        return startType == StartType.AUTO;
    }
}
