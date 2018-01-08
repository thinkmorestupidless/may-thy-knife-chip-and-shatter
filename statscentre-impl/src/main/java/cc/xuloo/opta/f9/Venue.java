package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Venue")
public class Venue implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String id;
    
    @XStreamAlias("Name")
    public String name;
    
    @XStreamAlias("Country")
    public String country;

    @Override
    public String toString() {
        return "Venue [id=" + id + ", name=" + name + ", country=" + country + "]";
    }
}
