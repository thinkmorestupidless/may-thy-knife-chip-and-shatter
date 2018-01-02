package cc.xuloo.betfair.client.settings;

import com.typesafe.config.Config;
import lombok.Value;

@Value
public class StreamSettings {

    public static StreamSettings fromConfig(Config config) {
        return new StreamSettings(
                config.getString("betfair.stream.uri"),
                config.getInt("betfair.stream.port"));
    }

    private final String uri;

    private final int port;
}
