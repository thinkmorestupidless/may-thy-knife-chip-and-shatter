package cc.xuloo.opta;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("MatchInfo")
public class MatchInfo implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("MatchDay")
    public int matchDay;
    
    @XStreamAsAttribute
    @XStreamAlias("MatchType")
    public String matchType;
    
    @XStreamAsAttribute
    @XStreamAlias("MatchWinner")
    public String matchWinner;
    
    @XStreamAsAttribute
    @XStreamAlias("Period")
    public String period;
    
    @XStreamAsAttribute
    @XStreamAlias("Venue_id")
    public String venueId;
    
    @XStreamAlias("Date")
    public Date date;
    
    @XStreamAlias("TZ")
    public String tz;
    
    public long getStadiumId() {
        return Long.parseLong(venueId.replaceAll("\\D+",""));
    }
    
    public long getMatchWinnerId() {
        if (StringUtils.isNotEmpty(matchWinner)) {
            return Long.parseLong(matchWinner.replaceAll("\\D+",""));
        }
        
        return 0;
    }
    
    public DateTime getKickoff() {
        return new DateTime(date);
    }
    
    public int getMatchDay() {
        return 0;
    }
}
