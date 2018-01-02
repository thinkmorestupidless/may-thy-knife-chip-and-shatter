package cc.xuloo.betfair.client.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConnectMessage implements RequestMessage {

    private final Integer id;

    private final String host;

    private final Integer port;

    public ConnectMessage(@JsonProperty("id") Integer id,
                          @JsonProperty("host") String host,
                          @JsonProperty("port") Integer port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }
}
