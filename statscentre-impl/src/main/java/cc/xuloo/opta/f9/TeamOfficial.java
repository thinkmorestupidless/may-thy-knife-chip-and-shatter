package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("TeamOfficial")
public class TeamOfficial implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAlias("PersonName")
    public PersonName person;

    @Override
    public String toString() {
        return "TeamOfficial [type=" + type + ", uid=" + uid + ", person=" + person + "]";
    }
}
