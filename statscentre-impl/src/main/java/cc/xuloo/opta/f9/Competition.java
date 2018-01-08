package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Competition")
public class Competition implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAlias("Country")
    public String country;
    
    @XStreamAlias("Name")
    public String name;
    
    @XStreamImplicit
    public Set<Stat> stats;

    @Override
    public String toString() {
        return "Competition [uid=" + uid + ", country=" + country + ", name=" + name + ", stats=" + stats
               + "]";
    }
}
