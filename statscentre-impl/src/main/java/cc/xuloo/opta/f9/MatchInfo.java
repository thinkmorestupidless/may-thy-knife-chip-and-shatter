package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("MatchInfo")
public class MatchInfo implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("MatchType")
    public String matchType;
    
    @XStreamAsAttribute
    @XStreamAlias("Period")
    public String period;
    
    @XStreamAsAttribute
    @XStreamAlias("TimeStamp")
    public Date timeStamp;
    
    @XStreamAsAttribute
    @XStreamAlias("Weather")
    public String weather;
    
    @XStreamAlias("Attendance")
    public int attendance;
    
    @XStreamAlias("Date")
    public Date date;
    
    @XStreamAlias("Result")
    public Result result;

    @Override
    public String toString() {
        return "MatchInfo [matchType=" + matchType + ", period=" + period + ", timeStamp=" + timeStamp
               + ", weather=" + weather + ", attendance=" + attendance + ", date=" + date + ", result="
               + result + "]";
    }
}
