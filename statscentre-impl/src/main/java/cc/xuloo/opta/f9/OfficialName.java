package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("OfficialName")
public class OfficialName implements Serializable {

    @XStreamAlias("First")
    public String first;
    
    @XStreamAlias("Last")
    public String last;
    
    public String getFullName() {
        return first + " " + last;
    }

    @Override
    public String toString() {
        return "OfficialName [first=" + first + ", last=" + last + "]";
    }
}
