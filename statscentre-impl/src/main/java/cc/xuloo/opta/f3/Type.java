package cc.xuloo.opta.f3;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Type")
public class Type implements Serializable {

    @XStreamAsAttribute
    public String qualify;
    
    @XStreamImplicit
    public Set<Team> teams;

    @Override
    public String toString() {
        return "Type [qualify=" + qualify + ", teams=" + teams + "]";
    }
}
