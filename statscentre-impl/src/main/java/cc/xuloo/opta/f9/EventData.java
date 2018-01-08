package cc.xuloo.opta.f9;

import java.util.Date;

public interface EventData {
    
    long getId();

    long getPlayer1();
    
    long getPlayer2();
    
    int getGameTime();
    
    String getPosition();
    
    String getReason();
    
    Date getTimestamp();
    
    String getType();
}
