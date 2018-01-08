package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("Substitution")
public class Substitution implements Serializable, EventData {
    
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
    public Integer period;
    
    @XStreamAsAttribute
    @XStreamAlias("Reason")
    public String reason;
    
    @XStreamAsAttribute
    @XStreamAlias("SubOff")
    public String subOff;
    
    @XStreamAsAttribute
    @XStreamAlias("SubOn")
    public String subOn;
    
    @XStreamAsAttribute
    @XStreamAlias("SubstitutePosition")
    public Integer substitutePosition;
    
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
        return Long.parseLong(subOn.replaceAll("\\D+",""));
    }

    @Override
    public long getPlayer2() {
        return Long.parseLong(subOff.replaceAll("\\D+",""));
    }

    @Override
    public int getGameTime() {
        return time;
    }

    @Override
    public String getPosition() {
        return String.valueOf(substitutePosition);
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public Date getTimestamp() {
        return timeStamp;
    }
    
    @Override
    public String getType() {
        return "substitution";
    }

    @Override
    public String toString() {
        return "Substitution [uid=" + uid + ", eventID=" + eventID + ", eventNumber=" + eventNumber
               + ", period=" + period + ", reason=" + reason + ", subOff=" + subOff + ", subOn=" + subOn
               + ", substitutePosition=" + substitutePosition + ", time=" + time + ", timeStamp=" + timeStamp
               + "]";
    }
}
