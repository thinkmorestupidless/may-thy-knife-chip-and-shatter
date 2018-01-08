package cc.xuloo.opta.f3;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SoccerFeed")
public class F3 extends AbstractSportData {

    @XStreamAlias("SoccerDocument")
    public SoccerDocument soccerDocument;

    @Override
    public String toString() {
        return "SoccerFeed [soccerDocument=" + soccerDocument + ", meta=" + meta
               + "]";
    }
}
