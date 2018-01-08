package cc.xuloo.opta;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("CurrentTeamOnly")
public class CurrentTeamOnly implements Serializable {

    @XStreamImplicit
    public Set<Stat> stats;

    @Override
    public String toString() {
        return "CurrentTeamOnly [stats=" + stats + "]";
    }
}
