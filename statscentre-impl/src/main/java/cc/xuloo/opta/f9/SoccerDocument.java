package cc.xuloo.opta.f9;

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
    @XStreamAlias("uID")
    public String id;

    @XStreamAlias("Competition")
    public Competition competition;
    
    @XStreamImplicit
    public Set<MatchData> matchData;
    
    @XStreamImplicit
    public Set<Team> teams;
    
    @XStreamAlias("Venue")
    public Venue venue;
    
    public long getId() {
        return Long.parseLong(id.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "SoccerDocument [Type=" + type + ", id=" + id + ", competition=" + competition
               + ", matchData=" + matchData + ", teams=" + teams + ", venue=" + venue + "]";
    }
}
