package cc.xuloo.opta.f40;

import cc.xuloo.opta.Constants;
import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Date;
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
        try {
            return Integer.parseInt(stats.stream()
                                         .filter(stat -> key.equals(stat.type))
                                         .findFirst()
                                         .orElseGet(() -> new Stat(key, "0"))
                                         .value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public String getString(String key) {
        return stats.stream()
                    .filter(stat -> key.equals(stat.type))
                    .findFirst()
                    .orElseGet(() -> new Stat(key, ""))
                    .value;
    }
    
    public Date getDate(String key) {
        try {
        return Constants.dateformat
                        .parseDateTime(stats.stream()
                                            .filter(stat -> key.equals(stat.type))
                                            .findFirst()
                                            .orElseGet(() -> new Stat(key, "0000-01-01"))
                                            .value)
                        .toDate();
        } catch (IllegalArgumentException e) {
            return Constants.dateformat.parseDateTime("0000-01-01").toDate();
        }
    }
    
    @Override
    public String toString() {
        return "Player [uid=" + uid + ", name=" + name + ", position=" + position + ", stats=" + stats + "]";
    }
}
