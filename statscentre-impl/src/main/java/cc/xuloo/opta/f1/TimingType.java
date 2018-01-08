package cc.xuloo.opta.f1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("TimingType")
public class TimingType implements Serializable {

    @XStreamAsAttribute
    public String name;
    
    @XStreamAsAttribute
    @XStreamAlias("timestamp_accuracy_id")
    public int timestampAccuracyId;
}
