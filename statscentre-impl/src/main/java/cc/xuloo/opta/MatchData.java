package cc.xuloo.opta;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@XStreamAlias("MatchData")
public class MatchData implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("detail_id")
    public int detailId;
      
    @XStreamAsAttribute
    @XStreamAlias("last_modified")
    public Date lastModified;
    
    @XStreamAsAttribute
    @XStreamAlias("timestamp_accuracy_id")
    public int timestampAccuracyId;
    
    @XStreamAsAttribute
    @XStreamAlias("timing_id")
    public int timingId;
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uID;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    @XStreamImplicit
    public Set<TeamData> teamData;
    
    @XStreamAlias("MatchInfo")
    public MatchInfo info;
    
    public long getId() {
        return Long.parseLong(uID.replaceAll("\\D+",""));
    }
    
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
}
