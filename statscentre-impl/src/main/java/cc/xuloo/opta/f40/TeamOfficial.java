package cc.xuloo.opta.f40;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("TeamOfficial")
public class TeamOfficial implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
    @XStreamAsAttribute
    public String country;
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAlias("PersonName")
    public PersonName person;
    
    public long getId() {
        return Long.parseLong(uid.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "TeamOfficial [type=" + type + ", country=" + country + ", uid=" + uid + ", person=" + person
               + "]";
    }
}
