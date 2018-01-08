package cc.xuloo.opta.f1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("DetailType")
public class DetailType implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("detail_id")
    public String detailId;
    
    @XStreamAsAttribute
    @XStreamAlias("name")
    public String name;
}
