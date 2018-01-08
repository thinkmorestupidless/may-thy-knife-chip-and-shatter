package cc.xuloo.opta.f1;

import cc.xuloo.opta.MatchData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("SoccerDocument")
public class SoccerDocument implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String Type;
    
    @XStreamAsAttribute
    @XStreamAlias("competition_code")
    public String competition_code;
    
    @XStreamAsAttribute
    @XStreamAlias("competition_id")
    public Long competition_id;
    
    @XStreamAsAttribute
    @XStreamAlias("competition_name")
    public String competition_name;
    
    @XStreamAsAttribute
    @XStreamAlias("season_id")
    public Long season_id;
    
    @XStreamAsAttribute
    @XStreamAlias("season_name")
    public String season_name;
    
    @XStreamImplicit
    public Set<MatchData> matchData;
    
    @XStreamAlias("TimingTypes")
    public TimingTypes timingTypes;
    
    @XStreamImplicit
    public Set<Team> teams;
}
