package cc.xuloo.opta.f15;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("MatchData")
public class MatchData implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uID;
    
    @XStreamImplicit
    public Set<TeamData> teamData;
}
