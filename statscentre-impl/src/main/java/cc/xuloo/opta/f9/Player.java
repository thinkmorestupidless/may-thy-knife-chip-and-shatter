package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Player")
public class Player implements Serializable {
    
    @XStreamAlias("uID")
    @XStreamAsAttribute
    public String id;
    
    @XStreamAlias("Position")
    public String position;
    
    @XStreamAlias("PersonName")
    public PersonName personName;
    
    public long getId() {
        return Long.parseLong(id.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", position=" + position + ", personName=" + personName + "]";
    }
}
