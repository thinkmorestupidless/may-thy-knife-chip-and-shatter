package cc.xuloo.opta;

import cc.xuloo.opta.f3.TeamStandings;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Competition")
public class Competition implements Serializable {

    @XStreamAlias("TeamStandings")
    public TeamStandings teamStandings;

    @Override
    public String toString() {
        return "Competition [teamStandings=" + teamStandings + "]";
    }
}
