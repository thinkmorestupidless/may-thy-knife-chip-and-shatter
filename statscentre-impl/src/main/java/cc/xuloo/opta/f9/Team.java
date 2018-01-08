package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Team")
public class Team implements Serializable {
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String id;
    
    @XStreamAlias("Name")
    public String name;
    
    @XStreamAlias("Country")
    public String country;
    
    @XStreamImplicit
    public Set<Player> players;
    
    @XStreamImplicit
    public Set<TeamOfficial> teamOfficials;
    
    public long getId() {
        return Long.parseLong(id.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", country=" + country + ", players=" + players
               + ", teamOfficials=" + teamOfficials + "]";
    }
}
