package cc.xuloo.opta.f1;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.joda.time.DateTime;

import java.util.stream.Collectors;

@XStreamAlias("SoccerFeed")
public class F1 extends AbstractSportData {

    @XStreamAlias("SoccerDocument")
    public SoccerDocument soccerDocument;
    
    public int timeSlotFor(DateTime kickoff) {
        return new Timeslots(soccerDocument.matchData.stream().map(md -> md.info.getKickoff()).collect(Collectors.toSet())).indexOf(kickoff);
    }

    @Override
    public String toString() {
        return "SoccerFeed [soccerDocument=" + soccerDocument + ", meta=" + meta
               + "]";
    }
}
