package cc.xuloo.opta.f1;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("TimingTypes")
public class TimingTypes implements Serializable {

    @XStreamAlias("DetailTypes")
    public Set<DetailType> detailTypes;
    
    @XStreamAlias("TimestampAccuracyTypes")
    public Set<TimestampAccuracyType> timestampAccuracyTypes;
    
    @XStreamAlias("TimingType")
    public Set<TimingType> timingTypes;
}
