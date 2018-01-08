package cc.xuloo.opta.f13;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("message")
public class Message implements Serializable {

    @XStreamAsAttribute
    public long id;
    
    @XStreamAsAttribute
    public String comment;
    
    @XStreamAsAttribute
    @XStreamAlias("last_modified")
    public Date lastModified;
    
    @XStreamAsAttribute
    @XStreamAlias("last_modified_utc")
    public Date lastModifiedUtc;
    
    @XStreamAsAttribute
    public int minute;
    
    @XStreamAsAttribute
    public int period;
    
    @XStreamAsAttribute
    public int second;
    
    @XStreamAsAttribute
    public String time;
    
    @XStreamAsAttribute
    public String type;

    @Override
    public String toString() {
        return "Message [id=" + id + ", comment=" + comment + ", lastModified=" + lastModified
               + ", lastModifiedUtc=" + lastModifiedUtc + ", minute=" + minute + ", period=" + period
               + ", second=" + second + ", time=" + time + ", type=" + type + "]";
    }
}
