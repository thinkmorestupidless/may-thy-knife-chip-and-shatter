package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("AssistantOfficial")
public class AssistantOfficial implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("FirstName")
    public String firstName;
    
    @XStreamAsAttribute
    @XStreamAlias("LastName")
    public String lastName;
    
    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;

    @Override
    public String toString() {
        return "AssistantOfficial [firstName=" + firstName + ", lastName=" + lastName + ", type=" + type
               + ", uid=" + uid + "]";
    }
}
