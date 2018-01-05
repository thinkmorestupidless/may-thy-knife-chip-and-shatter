package cc.xuloo.betfair.client.settings;

import com.typesafe.config.Config;
import lombok.Value;

import java.io.File;

@Value
public class BetfairCredentials {

    public static BetfairCredentials fromConfig(Config config) {
        return new BetfairCredentials(
                new File(config.getString("betfair.client.credentials.certFile")),
                new File(config.getString("betfair.client.credentials.keyFile")),
                config.getString("betfair.client.credentials.keyPassword"),
                config.getString("betfair.client.credentials.applicationKey"),
                config.getString("betfair.client.credentials.username"),
                config.getString("betfair.client.credentials.password"));
    }

    private final File certFile;

    private final File keyFile;

    private final String keyPassword;

    private final String applicationKey;

    private final String username;

    private final String password;
}
