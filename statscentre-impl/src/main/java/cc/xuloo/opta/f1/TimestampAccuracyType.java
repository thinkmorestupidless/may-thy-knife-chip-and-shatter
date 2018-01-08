package cc.xuloo.opta.f1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("TimestampAccuracyType")
public class TimestampAccuracyType implements Serializable {

    @XStreamAsAttribute
    public String name;

    @XStreamAsAttribute
    @XStreamAlias("timing_id")
    public int timingId;
}
