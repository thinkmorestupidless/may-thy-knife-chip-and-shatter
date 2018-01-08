package cc.xuloo.opta.f9;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("MatchPlayer")
public class MatchPlayer implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Captain")
    public boolean captain;
    
    @XStreamAsAttribute
    @XStreamAlias("PlayerRef")
    public String playerRef;
    
    @XStreamAsAttribute
    @XStreamAlias("Position")
    public String position;
    
    @XStreamAsAttribute
    @XStreamAlias("SubPosition")
    public String subPosition;
    
    @XStreamAsAttribute
    @XStreamAlias("ShirtNumber")
    public String shirtNumber;
    
    @XStreamAsAttribute
    @XStreamAlias("Status")
    public String status;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    public long getId() {
        return Long.parseLong(playerRef.replaceAll("\\D+",""));
    }
    
    public boolean isInPlay() {
        return "start".equals(status.toLowerCase());
    }
    
    public int getInt(String key) {
        return Integer.parseInt(stats.stream()
                                     .filter(stat -> key.equals(stat.type))
                                     .findFirst()
                                     .orElseGet(() -> new Stat(key, "0"))
                                     .value);
    }

    @Override
    public String toString() {
        return "MatchPlayer [captain=" + captain + ", playerRef=" + playerRef + ", position=" + position
               + ", subPosition=" + subPosition + ", shirtNumber=" + shirtNumber + ", status=" + status
               + ", stats=" + stats + "]";
    }
}
