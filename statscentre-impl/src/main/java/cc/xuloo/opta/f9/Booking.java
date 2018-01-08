package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("Booking")
public class Booking implements Serializable, EventData {

    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAsAttribute
    @XStreamAlias("Card")
    public String card;
    
    @XStreamAsAttribute
    @XStreamAlias("CardType")
    public String cardType;
    
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
    @XStreamAlias("Reason")
    public String reason;
    
    @XStreamAsAttribute
    @XStreamAlias("Time")
    public Integer time;
    
    @XStreamAsAttribute
    @XStreamAlias("TimeStamp")
    public Date timeStamp;
    
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
        return card;
    }

    @Override
    public Date getTimestamp() {
        return timeStamp;
    }
    
    @Override
    public String getType() {
        return card;
    }

    @Override
    public String toString() {
        return "Booking [uid=" + uid + ", card=" + card + ", cardType=" + cardType + ", eventID=" + eventID
               + ", eventNumber=" + eventNumber + ", period=" + period + ", playerRef=" + playerRef
               + ", reason=" + reason + ", time=" + time + ", timeStamp=" + timeStamp + "]";
    }
}
