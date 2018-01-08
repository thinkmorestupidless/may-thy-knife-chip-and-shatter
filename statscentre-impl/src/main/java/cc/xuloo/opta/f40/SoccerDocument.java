package cc.xuloo.opta.f40;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Collections;
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
    public Set<Team> teams;
    
    @XStreamAlias("PlayerChanges")
    public Set<Team> transfers;
    
    public Set<Team> transfers() {
        if (transfers == null) {
            return Collections.emptySet();
        }
        
        return transfers;
    }

    @Override
    public String toString() {
        return "SoccerDocument [Type=" + Type + ", competition_code=" + competition_code + ", competition_id="
               + competition_id + ", competition_name=" + competition_name + ", season_id=" + season_id
               + ", season_name=" + season_name + ", teams=" + teams + ", transfers=" + transfers + "]";
    }
}
