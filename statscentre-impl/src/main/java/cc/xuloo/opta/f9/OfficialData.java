package cc.xuloo.opta.f9;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("OfficialData")
public class OfficialData implements Serializable {

    @XStreamAlias("OfficialRef")
    public OfficialRef officialRef;

    @Override
    public String toString() {
        return "OfficialData [officialRef=" + officialRef + "]";
    }
}
