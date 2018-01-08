package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("MissedPenalty")
public class MissedPenalty implements Serializable, EventData {

    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;

    @XStreamAsAttribute
    @XStreamAlias("EventID")
    public Long eventID;
    
    @XStreamAsAttribute
    @XStreamAlias("EventNumber")
    public Integer eventNumber;
    
    @XStreamAsAttribute
    @XStreamAlias("Period")
    public String period;
    
    @XStreamAsAttribute
    @XStreamAlias("PlayerRef")
    public String playerRef;
    
    @XStreamAsAttribute
    @XStreamAlias("Time")
    public Integer time;
    
    @XStreamAsAttribute
    @XStreamAlias("TimeStamp")
    public Date timeStamp;
    
    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
    @Override
    public long getId() {
        return eventID;
    }
    
    @Override
    public long getPlayer1() {
        return Long.parseLong(playerRef.replaceAll("\\D+",""));
    }

    @Override
    public long getPlayer2() {
        return 0l;
    }

    @Override
    public int getGameTime() {
        return time;
    }

    @Override
    public String getPosition() {
        return "";
    }

    @Override
    public String getReason() {
        return type;
    }

    @Override
    public Date getTimestamp() {
        return timeStamp;
    }
    
    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MissedPenalty [uid=" + uid + ", eventID=" + eventID + ", eventNumber=" + eventNumber
               + ", period=" + period + ", playerRef=" + playerRef + ", time=" + time + ", timeStamp="
               + timeStamp + ", type=" + type + "]";
    }
}
