package cc.xuloo.opta.f30;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Set;

@XStreamAlias("SeasonStatistics")
public class F30 extends AbstractSportData {
    
    @XStreamImplicit
    public Set<Team> teams;

    @Override
    public String toString() {
        return "SeasonStatistics [teams=" + teams + ", meta=" + meta + "]";
    }
}
