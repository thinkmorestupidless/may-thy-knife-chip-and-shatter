package cc.xuloo.opta.f40;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Stadium")
public class Stadium implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;

    @XStreamAlias("Capacity")
    public int capacity;
    
    @XStreamAlias("Name")
    public String name;
    
    public long getId() {
        return Long.parseLong(uid.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "Stadium [uid=" + uid + ", capacity=" + capacity + ", name=" + name + "]";
    }
}
