package cc.xuloo.opta.f9;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.Date;

@XStreamAlias("SoccerFeed")
public class F9 extends AbstractSportData {

    @XStreamAsAttribute
    @XStreamAlias("TimeStamp")
    public Date timestamp;
    
    @XStreamAlias("SoccerDocument")
    public SoccerDocument soccerDocument;
    
    public FixtureStatus getStatus(MatchData matchData) {
        String type = soccerDocument.type.toLowerCase();
        String period = matchData.info.period.toLowerCase();
        String result = matchData.info.result.type.toLowerCase();
        
        if (("result".equals(type) && "fulltime".equals(period)) || "abandoned".equals(result)) {
            return FixtureStatus.completed;
        }
        
        if (("latest".equals(type) && "prematch".equals(period)) || "postponed".equals(result)) {
            return FixtureStatus.upcoming;
        }
        
        return FixtureStatus.live;
    }

    @Override
    public String toString() {
        return "SoccerFeed [timestamp=" + timestamp + ", soccerDocument=" + soccerDocument + ", meta=" + meta
               + "]";
    }
}
