package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("OfficialRef")
public class OfficialRef implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;

    @Override
    public String toString() {
        return "OfficialRef [type=" + type + "]";
    }
}
