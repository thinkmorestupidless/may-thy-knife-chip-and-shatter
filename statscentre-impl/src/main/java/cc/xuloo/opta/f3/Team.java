package cc.xuloo.opta.f3;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Team")
public class Team implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAlias("Name")
    public String name;
    
    public long getId() {
        return Long.parseLong(uid.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "Team [uid=" + uid + ", name=" + name + "]";
    }
}
