package cc.xuloo.opta.f15;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Player")
public class Player implements Serializable {
    
    @XStreamAlias("uID")
    @XStreamAsAttribute
    public String uid;

    @XStreamAlias("Name")
    public String name;
    
    @XStreamAlias("Position")
    public String position;
    
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
        return "Player [uid=" + uid + ", name=" + name + ", position=" + position + ", stats=" + stats + "]";
    }
}
