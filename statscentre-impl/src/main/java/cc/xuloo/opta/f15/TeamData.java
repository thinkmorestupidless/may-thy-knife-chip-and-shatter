package cc.xuloo.opta.f15;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("TeamData")
public class TeamData implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAsAttribute
    @XStreamAlias("Side")
    public String side;
}
