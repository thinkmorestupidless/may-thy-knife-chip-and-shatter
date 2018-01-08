package cc.xuloo.opta;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Stat")
public class Stat implements Serializable {

    public String type;
    
    public String value;
    
    public Stat() {
        
    }
    
    public Stat(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Stat [type=" + type + ", value=" + value + "]";
    }
}
