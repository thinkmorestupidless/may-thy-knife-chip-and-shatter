package cc.xuloo.opta.f15;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Team")
public class Team implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAlias("Name")
    public String name;
    
    @XStreamImplicit
    public Set<Player> players;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    public long getId() {
        return Long.parseLong(uid.replaceAll("\\D+",""));
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
        return "Team [uid=" + uid + ", name=" + name + ", players=" + players + ", stats=" + stats + "]";
    }
}
