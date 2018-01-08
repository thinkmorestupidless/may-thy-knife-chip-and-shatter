package cc.xuloo.opta.f4;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("message")
public class Message implements Serializable {

    @XStreamAsAttribute
    public long id;
    
    @XStreamAsAttribute
    public String fact;

    @Override
    public String toString() {
        return "Message [id=" + id + ", fact=" + fact + "]";
    }
}
