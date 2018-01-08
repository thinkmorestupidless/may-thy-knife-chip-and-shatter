package cc.xuloo.opta.f3;

import cc.xuloo.opta.Competition;
import cc.xuloo.opta.Qualification;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("SoccerDocument")
public class SoccerDocument implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
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
    
    @XStreamAlias("Competition")
    public Competition competition;
    
    @XStreamAlias("Qualification")
    public Qualification qualification;
    
    @XStreamImplicit
    public Set<Team> teams;

    @Override
    public String toString() {
        return "SoccerDocument [type=" + type + ", competition_code=" + competition_code + ", competition_id="
               + competition_id + ", competition_name=" + competition_name + ", season_id=" + season_id
               + ", season_name=" + season_name + ", competition=" + competition + ", qualification="
               + qualification + ", teams=" + teams + "]";
    }
}
