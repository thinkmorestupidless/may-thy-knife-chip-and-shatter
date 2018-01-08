package cc.xuloo.opta.f30;

import cc.xuloo.opta.CurrentTeamOnly;
import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Player")
public class Player implements Serializable {
    
    private static final Logger log = LoggerFactory.getLogger(Player.class);
    
    @XStreamAsAttribute
    @XStreamAlias("first_name")
    public String firstName;
    
    @XStreamAsAttribute
    @XStreamAlias("last_name")
    public String lastName;
    
    @XStreamAsAttribute
    @XStreamAlias("player_id")
    public Long playerId;
    
    @XStreamAsAttribute
    public int shirtNumber;
    
    @XStreamAlias("CurrentTeamOnly")
    public CurrentTeamOnly currentTeamOnly;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    public long getId() {
        return playerId;
    }
    
    public int getInt(String key) {
        if (stats == null) {
            return 0;
        }
        
        return Integer.parseInt(stats.stream()
                                     .filter(stat -> key.equals(stat.type))
                                     .findFirst()
                                     .orElseGet(() -> new Stat(key, "0"))
                                     .value);
    }

    @Override
    public String toString() {
        return "Player [firstName=" + firstName + ", lastName=" + lastName + ", playerId=" + playerId
               + ", shirtNumber=" + shirtNumber + ", currentTeamOnly=" + currentTeamOnly + ", stats=" + stats
               + "]";
    }
}
