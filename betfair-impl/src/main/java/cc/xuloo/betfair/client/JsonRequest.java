package cc.xuloo.betfair.client;

import com.google.common.collect.Maps;
import lombok.Value;

import java.util.Map;

@Value
public class JsonRequest {

    private final String jsonrpc = "2.0";

    private final String id = "1";

    private final String method;

    private final Map<String, Object> params;

    public static JsonRequest create() {
        return new JsonRequest("", Maps.newHashMap());
    }

    public JsonRequest withMethod(String method) {
        return new JsonRequest(method, params);
    }

    public JsonRequest withParam(String key, Object value) {
        Map<String, Object> map = params;

        if (map == null) {
            map = Maps.newHashMap();
        }

        if (value != null) {
            map.put(key, value);
        }

        return new JsonRequest(method, map);
    }
}
