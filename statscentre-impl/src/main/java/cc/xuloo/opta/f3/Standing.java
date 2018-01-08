package cc.xuloo.opta.f3;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Standing")
public class Standing implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("Type")
    public String type;
    
    @XStreamAlias("Against")
    public int against;
    
    @XStreamAlias("AwayAgainst")
    public int awayAgainst;
    
    @XStreamAlias("AwayDrawn")
    public int awayDrawn;
    
    @XStreamAlias("AwayFor")
    public int awayFor;
    
    @XStreamAlias("AwayLost")
    public int awayLost;
    
    @XStreamAlias("AwayPlayed")
    public int awayPlayed;
    
    @XStreamAlias("AwayPoints")
    public int awayPoints;
    
    @XStreamAlias("AwayPosition")
    public int awayPosition;
    
    @XStreamAlias("AwayWon")
    public int awayWon;
    
    @XStreamAlias("Drawn")
    public int drawn;
    
    @XStreamAlias("For")
    public int _for;
    
    @XStreamAlias("HomeAgainst")
    public int homeAgainst;
    
    @XStreamAlias("HomeDrawn")
    public int homeDrawn;
    
    @XStreamAlias("HomeFor")
    public int homeFor;
    
    @XStreamAlias("HomeLost")
    public int homeLost;
    
    @XStreamAlias("HomePlayed")
    public int homePlayed;
    
    @XStreamAlias("HomePoints")
    public int homePoints;
    
    @XStreamAlias("HomePosition")
    public int homePosition;
    
    @XStreamAlias("HomeWon")
    public int homeWon;
    
    @XStreamAlias("Lost")
    public int lost;
    
    @XStreamAlias("Played")
    public int played;
    
    @XStreamAlias("Points")
    public int points;
    
    @XStreamAlias("Position")
    public int position;
    
    @XStreamAlias("StartDayPosition")
    public int startDayPosition;
    
    @XStreamAlias("Won")
    public int won;
    
    public String getChange() {
        return getChange(startDayPosition, position);
    }
    
    public String getChange(int a, int b) {
        if (a > b)
            return "up";
        if (a < b)
            return "down";
        return "none";
    }

    @Override
    public String toString() {
        return "Standing [type=" + type + ", against=" + against + ", awayAgainst=" + awayAgainst
               + ", awayDrawn=" + awayDrawn + ", awayFor=" + awayFor + ", awayLost=" + awayLost
               + ", awayPlayed=" + awayPlayed + ", awayPoints=" + awayPoints + ", awayPosition="
               + awayPosition + ", awayWon=" + awayWon + ", drawn=" + drawn + ", _for=" + _for
               + ", homeAgainst=" + homeAgainst + ", homeDrawn=" + homeDrawn + ", homeFor=" + homeFor
               + ", homeLost=" + homeLost + ", homePlayed=" + homePlayed + ", homePoints=" + homePoints
               + ", homePosition=" + homePosition + ", homeWon=" + homeWon + ", lost=" + lost + ", played="
               + played + ", Points=" + points + ", Position=" + position + ", StartDayPosition="
               + startDayPosition + ", Won=" + won + "]";
    }
}
