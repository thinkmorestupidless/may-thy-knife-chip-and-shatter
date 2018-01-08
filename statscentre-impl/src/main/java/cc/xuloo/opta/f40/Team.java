package cc.xuloo.opta.f40;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.Set;

@XStreamAlias("Team")
public class Team implements Serializable {
    
    @XStreamAsAttribute
    public String city;
    
    @XStreamAsAttribute
    public String country;
    
    @XStreamAsAttribute
    @XStreamAlias("country_id")
    public Integer countryId;
    
    @XStreamAsAttribute
    @XStreamAlias("country_iso")
    public String countryIso;
    
    @XStreamAsAttribute
    @XStreamAlias("postal_code")
    public String postalCode;
    
    @XStreamAsAttribute
    @XStreamAlias("region_id")
    public Integer regionId;
    
    @XStreamAsAttribute
    @XStreamAlias("region_name")
    public String regionName;
    
    @XStreamAsAttribute
    @XStreamAlias("short_club_name")
    public String shortClubName;
    
    @XStreamAsAttribute
    public String street;
    
    @XStreamAsAttribute
    @XStreamAlias("uID")
    public String uid;
    
    @XStreamAsAttribute
    @XStreamAlias("web_address")
    public String webAddress;

    @XStreamAlias("Founded")
    public Integer founded;
    
    @XStreamAlias("Name")
    public String name;
    
    @XStreamImplicit
    public Set<Player> players;
    
    @XStreamAlias("SYMID")
    public String symid;
    
    @XStreamAlias("Stadium")
    public Stadium stadium;
    
    @XStreamImplicit
    public Set<TeamOfficial> officials;
    
    @XStreamImplicit
    public Set<Stat> stats;
    
    public long getId() {
        return Long.parseLong(uid.replaceAll("\\D+",""));
    }

    @Override
    public String toString() {
        return "Team [city=" + city + ", country=" + country + ", countryId=" + countryId + ", countryIso="
               + countryIso + ", postalCode=" + postalCode + ", regionId=" + regionId + ", regionName="
               + regionName + ", shortClubName=" + shortClubName + ", street=" + street + ", uid=" + uid
               + ", webAddress=" + webAddress + ", founded=" + founded + ", name=" + name + ", players="
               + players + ", symid=" + symid + ", stadium=" + stadium + ", officials=" + officials
               + ", stats=" + stats + "]";
    }
}
