package cc.xuloo.opta.f3;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("TeamRecord")
public class TeamRecord implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("TeamRef")
    public String teamRef;
    
    @XStreamAlias("Standing")
    public Standing standing;
    
    public long getId() {
        return Long.parseLong(teamRef.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "TeamRecord [teamRef=" + teamRef + ", standing=" + standing + "]";
    }
}
