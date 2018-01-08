package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("PersonName")
public class PersonName implements Serializable {

    @XStreamAlias("First")
    public String first;
    
    @XStreamAlias("Last")
    public String last;
    
    @XStreamAlias("Known")
    public String known;

    @Override
    public String toString() {
        return "PersonName [first=" + first + ", last=" + last + ", known=" + known + "]";
    }
}
