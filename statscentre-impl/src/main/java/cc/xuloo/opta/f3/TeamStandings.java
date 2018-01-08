package cc.xuloo.opta.f3;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("TeamStandings")
public class TeamStandings implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Matchday")
    public int matchday;
    
    @XStreamImplicit
    public Set<TeamRecord> teamRecords;

    @Override
    public String toString() {
        return "TeamStandings [matchday=" + matchday + ", teamRecords=" + teamRecords + "]";
    }
}
