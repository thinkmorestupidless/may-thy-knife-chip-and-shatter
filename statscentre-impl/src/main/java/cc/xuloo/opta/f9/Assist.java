package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("Assist")
public class Assist implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("PlayerRef")
    public String playerRef;

    @Override
    public String toString() {
        return "Assist [playerRef=" + playerRef + "]";
    }
}
