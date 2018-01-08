package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@XStreamAlias("TeamData")
public class TeamData implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("Score")
    public Integer score;
    
    @XStreamAsAttribute
    @XStreamAlias("Side")
    public String side;
    
    @XStreamAsAttribute
    @XStreamAlias("TeamRef")
    public String teamRef;

    @XStreamImplicit
    public Set<Booking> bookings;
    
    @XStreamImplicit
    public Set<Goal> goals;
    
    @XStreamImplicit
    public Set<MissedPenalty> missedPenalties;
    
    @XStreamAlias("PlayerLineUp")
    public Set<MatchPlayer> players;
    
    @XStreamImplicit
    public Set<Substitution> substitutions;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    public long getId() {
        return Long.parseLong(teamRef.replaceAll("\\D+",""));
    }
    
    public PeriodData get(String key) {
        Stat stat = stats.stream().filter(s -> key.equals(s.type)).findFirst().orElseGet(() -> new Stat());
        
        return new PeriodData(key, new Number[] {new BigDecimal(stat.fh), new BigDecimal(stat.sh)}, new BigDecimal(Integer.parseInt(stat.fh) + Integer.parseInt(stat.sh)));
    }

    @Override
    public String toString() {
        return "TeamData [score=" + score + ", side=" + side + ", teamRef=" + teamRef + ", bookings="
               + bookings + ", goals=" + goals + ", missedPenalties=" + missedPenalties + ", players="
               + players + ", substitutions=" + substitutions + ", stats=" + stats + "]";
    }
}
