package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Stat")
public class Stat implements Serializable {

    public String fh;
    
    public String sh;
    
    public String type;
    
    public String value;

    @Override
    public String toString() {
        return "TeamStat [fh=" + fh + ", sh=" + sh + ", type=" + type + ", value=" + value + "]";
    }
}
