package cc.xuloo.opta;

import cc.xuloo.opta.f1.Goal;
import cc.xuloo.opta.f9.Booking;
import cc.xuloo.opta.f9.MissedPenalty;
import cc.xuloo.opta.f9.Substitution;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("TeamData")
public class TeamData implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("HalfScore")
    public int halfScore;
    
    @XStreamAsAttribute
    @XStreamAlias("Score")
    public int score;
    
    @XStreamAsAttribute
    @XStreamAlias("Side")
    public String side;
    
    @XStreamAsAttribute
    @XStreamAlias("TeamRef")
    public String teamRef;
    
    @XStreamImplicit
    public Set<Goal> goals;
    
    @XStreamImplicit
    public Set<Booking> bookings;
    
    @XStreamImplicit
    public Set<MissedPenalty> missedPenalties;
    
    @XStreamImplicit
    public Set<Substitution> substitutions;
    
    public long getId() {
        return Long.parseLong(teamRef.replaceAll("\\D+",""));
    }
}
