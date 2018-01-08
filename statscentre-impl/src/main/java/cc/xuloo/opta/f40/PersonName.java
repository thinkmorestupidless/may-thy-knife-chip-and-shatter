package cc.xuloo.opta.f40;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("PersonName")
public class PersonName implements Serializable {

    @XStreamAlias("BirthDate")
    public Date birthDate;
    
    @XStreamAlias("BirthPlace")
    public String birthPlace;
    
    @XStreamAlias("First")
    public String first;
    
    @XStreamAlias("Last")
    public String last;
    
    @XStreamAlias("join_date")
    public Date joinDate;

    @Override
    public String toString() {
        return "PersonName [birthDate=" + birthDate + ", birthPlace=" + birthPlace + ", first=" + first
               + ", last=" + last + ", joinDate=" + joinDate + "]";
    }
}
