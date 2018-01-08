package cc.xuloo.opta;

import cc.xuloo.opta.f3.Type;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Qualification")
public class Qualification implements Serializable {

    @XStreamImplicit
    public Set<Type> types;

    @Override
    public String toString() {
        return "Qualification [types=" + types + "]";
    }
}
