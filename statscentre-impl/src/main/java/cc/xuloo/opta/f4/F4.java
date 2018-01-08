package cc.xuloo.opta.f4;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Date;
import java.util.Set;

@XStreamAlias("Facts")
public class F4 extends AbstractSportData {

    @XStreamAsAttribute
    @XStreamAlias("away_team_id")
    public long awayTeamId;
    
    @XStreamAsAttribute
    @XStreamAlias("away_team_name")
    public String awayTeamName;
    
    @XStreamAsAttribute
    public String competition;
    
    @XStreamAsAttribute
    @XStreamAlias("competition_id")
    public long competitionId;
    
    @XStreamAsAttribute
    @XStreamAlias("game_date")
    public Date gameDate;
    
    @XStreamAsAttribute
    @XStreamAlias("game_id")
    public long gameId;
    
    @XStreamAsAttribute
    @XStreamAlias("home_team_id")
    public long homeTeamId;
    
    @XStreamAsAttribute
    @XStreamAlias("home_team_name")
    public String homeTeamName;
    
    @XStreamAsAttribute
    @XStreamAlias("lang_id")
    public String langId;
    
    @XStreamAsAttribute
    @XStreamAlias("last_modified")
    public Date lastModified;
    
    @XStreamAsAttribute
    public int matchday;
    
    @XStreamAsAttribute
    public String season;
    
    @XStreamAsAttribute
    @XStreamAlias("season_id")
    public long seasonId;
    
    @XStreamAsAttribute
    @XStreamAlias("sport_id")
    public long sportId;
    
    @XStreamAsAttribute
    @XStreamAlias("sport_name")
    public String sportName;
    
    @XStreamAsAttribute
    public String state="pre";
    
    @XStreamImplicit
    public Set<Message> facts;
}
