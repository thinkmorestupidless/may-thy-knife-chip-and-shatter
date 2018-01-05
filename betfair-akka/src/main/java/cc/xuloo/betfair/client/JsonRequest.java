package cc.xuloo.betfair.client;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class JsonRequest {

    private final String jsonrpc = "2.0";

    private final String id = "1";

    private final String method;

    @Singular
    private final Map<String, Object> params;
}
