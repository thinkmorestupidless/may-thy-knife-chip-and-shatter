package cc.xuloo.opta;

import cc.xuloo.opta.OptaConstants.FeedType;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class OptaMessage implements Serializable {

    final Map<String, String> headers;
    
    final String body;

    public OptaMessage(Map<String, String> headers, String body) {
        this.headers = headers;
        this.body = body;
    }
    
    public FeedType getFeedType() {
        return getHeader("x-meta-feed-type").map(s -> FeedType.valueOf(s)).get();
    }
    
    public Optional<String> getHeader(String key) {
        return Optional.ofNullable(headers.get(key));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
