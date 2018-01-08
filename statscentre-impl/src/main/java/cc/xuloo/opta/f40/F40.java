package cc.xuloo.opta.f40;

import cc.xuloo.opta.AbstractSportData;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.Date;

@XStreamAlias("SoccerFeed")
public class F40 extends AbstractSportData {

    @XStreamAsAttribute
    public Date timestamp;
    
    @XStreamAlias("SoccerDocument")
    public SoccerDocument soccerDocument;
    
    @Override
    public String toString() {
        return "SoccerFeed [timestamp=" + timestamp + ", soccerDocument=" + soccerDocument + ", meta=" + meta
               + "]";
    }
}
