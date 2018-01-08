package cc.xuloo.opta.f15;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SoccerFeed")
public class F15 extends AbstractSportData {
    
    @XStreamAlias("SoccerDocument")
    public SoccerDocument soccerDocument;

    @Override 
    public String toString() {
        return "SoccerFeed [soccerDocument=" + soccerDocument + ", meta=" + meta
               + "]";
    }
}
