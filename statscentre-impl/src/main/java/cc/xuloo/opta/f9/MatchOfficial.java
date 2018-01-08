package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("MatchOfficial")
public class MatchOfficial implements Serializable {

    public static MatchOfficial none() {
        MatchOfficial mo = new MatchOfficial();
        OfficialName on = new OfficialName();
        on.first = "";
        on.last = "";
        
        mo.name = on;
        
        return mo;
    }
    
    @XStreamAlias("OfficialData")
    public OfficialData data;
    
    @XStreamAlias("OfficialName")
    public OfficialName name;

    @Override
    public String toString() {
        return "MatchOfficial [data=" + data + ", name=" + name + "]";
    }
}
