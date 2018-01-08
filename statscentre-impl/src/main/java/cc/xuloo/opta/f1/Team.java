package cc.xuloo.opta.f1;

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

    @Override
    public String toString() {
        return "Team [uid=" + uid + ", name=" + name + "]";
    }
}
