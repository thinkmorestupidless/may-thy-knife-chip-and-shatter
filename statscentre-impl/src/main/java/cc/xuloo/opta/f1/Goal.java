package cc.xuloo.opta.f1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Goal")
public class Goal implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Period")
    public String Period;
    
    @XStreamAsAttribute
    @XStreamAlias("PlayerRef")
    public String PlayerRef;
    
    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String Type;
}
