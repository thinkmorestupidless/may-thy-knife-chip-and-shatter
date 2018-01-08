package cc.xuloo.opta;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("flip_sports_meta")
public class FlipSportsMeta implements Serializable {

    @XStreamAlias("comp_id")
    @XStreamAsAttribute
    public Long compId;
    
    @XStreamAlias("feed_gen_date")
    @XStreamAsAttribute
    public Date feedGenDate;
    
    @XStreamAlias("feed_id")
    @XStreamAsAttribute
    public Long feedId;
    
    @XStreamAlias("feed_supported_at_the_time")
    @XStreamAsAttribute
    public boolean feedSupportedAtTheTime;
    
    @XStreamAlias("feed_timestamp")
    @XStreamAsAttribute
    public Long feedTimestamp;
    
    @XStreamAlias("feed_type")
    @XStreamAsAttribute
    public String feedType;
    
    @XStreamAlias("file_name")
    @XStreamAsAttribute
    public String fileName;
    
    @XStreamAlias("formatted_timestamp")
    @XStreamAsAttribute
    public Date formattedTimestamp;
    
    @XStreamAlias("hash_key")
    @XStreamAsAttribute
    public String hashKey;
    
    @XStreamAlias("opta_comp_id")
    @XStreamAsAttribute
    public Long optaCompId;
    
    @XStreamAlias("opta_season_id")
    @XStreamAsAttribute
    public Long optaSeasonId;
    
    @XStreamAlias("path")
    @XStreamAsAttribute
    public String path;
    
    @XStreamAlias("processed")
    @XStreamAsAttribute
    public boolean processed;
    
    @XStreamAlias("s3_bucket")
    @XStreamAsAttribute
    public String s3Bucket;
    
    @XStreamAlias("scheduled_for_processing")
    @XStreamAsAttribute
    public boolean scheduledForProcessing;
    
    @XStreamAlias("sport_id")
    @XStreamAsAttribute
    public Long sportId;
    
    @XStreamAlias("timestamp")
    @XStreamAsAttribute
    public Long timestamp;
    
    @XStreamAlias("original_headers")
    public OriginalHeaders headers;

    @Override
    public String toString() {
        return "FlipSportsMeta [compId=" + compId + ", feedGenDate=" + feedGenDate + ", feedId=" + feedId
               + ", feedSupportedAtTheTime=" + feedSupportedAtTheTime + ", feedTimestamp=" + feedTimestamp
               + ", feedType=" + feedType + ", fileName=" + fileName + ", formattedTimestamp="
               + formattedTimestamp + ", hashKey=" + hashKey + ", optaCompId=" + optaCompId
               + ", optaSeasonId=" + optaSeasonId + ", path=" + path + ", processed=" + processed
               + ", s3Bucket=" + s3Bucket + ", scheduledForProcessing=" + scheduledForProcessing
               + ", sportId=" + sportId + ", timestamp=" + timestamp + ", headers=" + headers + "]";
    }
}
