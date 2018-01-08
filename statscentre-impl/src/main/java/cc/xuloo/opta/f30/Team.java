package cc.xuloo.opta.f30;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@XStreamAlias("Team")
public class Team implements Serializable {
    
    @XStreamAsAttribute
    public long id;
    
    @XStreamAsAttribute
    public String name;
    
    @XStreamImplicit
    public Set<Player> players;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    public long getId() {
        return id;
    }
    
    public int getInt(String key) {
        return Integer.parseInt(stats.stream()
                                     .filter(stat -> key.equals(stat.type))
                                     .findFirst()
                                     .orElseGet(() -> new Stat(key, "0"))
                                     .value);
    }
    
    public BigDecimal getDecimal(String key) {        
        return new BigDecimal(stats.stream()
                                   .filter(stat -> key.equals(stat.type))
                                   .findFirst()
                                   .orElseGet(() -> new Stat(key, "0"))
                                   .value);
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", stats=" + stats + "]";
    }   
}
