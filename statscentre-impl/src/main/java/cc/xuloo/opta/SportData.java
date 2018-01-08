package cc.xuloo.opta;

import cc.xuloo.opta.OptaConstants.FeedType;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public interface SportData extends Serializable {

    DateTime getCreatedAt();
    
    long getCompetitionId();

    long getSeasonId();
    
    FeedType getFeedType();
    
    FlipSportsMeta getMeta();
    
    void setHeaders(Map<String, Object> headers);
    
    Map<String, Object> getHeaders();
    
    boolean isPresent();
    
    static SportData none() {
        return NullSportData.instance();
    }
    
    public static class NullSportData implements SportData {
        
        private static SportData instance;
        
        public static SportData instance() {
            return instance = instance == null ? new NullSportData() : instance;
        }

        @Override
        public DateTime getCreatedAt() {
            return new DateTime(0);
        }

        @Override
        public long getCompetitionId() {
            return 0;
        }

        @Override
        public long getSeasonId() {
            return 0;
        }

        @Override
        public FeedType getFeedType() {
            return FeedType.unknown;
        }

        @Override
        public FlipSportsMeta getMeta() {
            return new FlipSportsMeta();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public void setHeaders(Map<String, Object> headers) {
            
        }

        @Override
        public Map<String, Object> getHeaders() {
            return Collections.emptyMap();
        }
    }
}
