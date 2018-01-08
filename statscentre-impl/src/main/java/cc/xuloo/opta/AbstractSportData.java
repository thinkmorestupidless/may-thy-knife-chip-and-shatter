package cc.xuloo.opta;

import cc.xuloo.opta.OptaConstants.FeedType;
import cc.xuloo.opta.SportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractSportData implements SportData, Serializable {

    @XStreamOmitField
    private Map<String, Object> headers;
    
    @XStreamAlias("flip_sports_meta")
    public FlipSportsMeta meta;
    
    @Override
    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }
    
    @Override
    public Map<String, Object> getHeaders() {
        if (headers != null) {
            return headers;
        }
        
        return Collections.emptyMap();
    }
    
    @Override
    public boolean isPresent() {
        return true;
    }
    
    @Override
    public DateTime getCreatedAt() {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        
        if (getMeta() == null) {
            return format.parseDateTime((String) headers.get("x-meta-production-server-timestamp"));
        }
        
        return new DateTime(getMeta().formattedTimestamp);
    }

    @Override
    public long getCompetitionId() {
        if (getMeta() == null) {
            return Long.parseLong((String) headers.get("x-meta-competition-id"));
        }
        
        return getMeta().optaCompId;
    }

    @Override
    public long getSeasonId() {
        if (getMeta() == null) {
            return Long.parseLong((String) headers.get("x-meta-season-id"));
        }
        
        return getMeta().optaSeasonId;
    }

    @Override
    public FeedType getFeedType() {
        if (getMeta() == null) {
            String str = (String) headers.get("x-meta-feed-type");
            return FeedType.valueOf(str.toLowerCase());
        }
        
        return FeedType.valueOf(getMeta().feedType.toLowerCase());
    }
    
    @Override
    public FlipSportsMeta getMeta() {
        return meta;
    }
}
