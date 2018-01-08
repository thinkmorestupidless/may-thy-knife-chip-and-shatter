package cc.xuloo.opta.f9;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("MatchData")
public class MatchData implements Serializable {
    
    @XStreamAlias("MatchInfo")
    public MatchInfo info;
    
    @XStreamImplicit
    public Set<MatchOfficial> matchOfficials;
    
    @XStreamAlias("AssistantOfficials")
    public Set<AssistantOfficial> assistantOfficials;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    @XStreamImplicit
    public Set<TeamData> teamData;
    
    public TeamData getHomeTeam() {
        return teamData.stream()
                       .filter(team -> team.side.toLowerCase().equals("home"))
                       .findFirst()
                       .orElseGet(() -> new TeamData());
    }
    
    public TeamData getAwayTeam() {
        return teamData.stream()
                       .filter(team -> team.side.toLowerCase().equals("away"))
                       .findFirst()
                       .orElseGet(() -> new TeamData());
    }
        
    public int getInt(String key) {
        return Integer.parseInt(stats.stream()
                                     .filter(stat -> key.equals(stat.type))
                                     .findFirst()
                                     .orElseGet(() -> new Stat(key, "0"))
                                     .value);
    }
    
    public MatchOfficial getMainReferee() {
        return matchOfficials.stream()
                             .filter(mo -> "main".equals(mo.data.officialRef.type.toLowerCase()))
                             .findFirst()
                             .orElseGet(() -> MatchOfficial.none());
    }

    @Override
    public String toString() {
        return "MatchData [info=" + info + ", matchOfficials=" + matchOfficials + ", assistantOfficials="
               + assistantOfficials + ", stats=" + stats + ", teamData=" + teamData + "]";
    }
}
