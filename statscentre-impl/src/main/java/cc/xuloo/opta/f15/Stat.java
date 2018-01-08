package cc.xuloo.opta.f15;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Stat")
public class Stat implements Serializable {

    public String type;
    
    public String value;

    @Override
    public String toString() {
        return "Stat [type=" + type + ", value=" + value + "]";
    }
}
