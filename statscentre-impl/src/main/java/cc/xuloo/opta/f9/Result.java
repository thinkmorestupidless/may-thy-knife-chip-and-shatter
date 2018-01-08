package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Result")
public class Result implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
    @XStreamAsAttribute
    @XStreamAlias("Winner")
    public String winner;

    @Override
    public String toString() {
        return "Result [type=" + type + ", winner=" + winner + "]";
    }
}
