package cc.xuloo.opta;

import java.util.Map;

public interface Parser extends FeedAware {

    SportData parse(String input, Map<String, Object> headers);
}
