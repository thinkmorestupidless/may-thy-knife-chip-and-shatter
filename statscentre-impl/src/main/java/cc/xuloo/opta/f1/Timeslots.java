package cc.xuloo.opta.f1;

import cc.xuloo.opta.Constants;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cc.xuloo.utils.CollectorUtils.toDistinctSortedList;
import static java.util.stream.Collectors.groupingBy;

public class Timeslots {

    final Map<DateTime, List<DateTime>> slotsByDate;
    
    public Timeslots(Collection<DateTime> kickoffs) {
        slotsByDate = kickoffs.stream()
                              .collect(groupingBy(dt -> dt.withTimeAtStartOfDay(), toDistinctSortedList()));
    }
    
    public int indexOf(DateTime kickoff) {
        DateTime date = kickoff.withTimeAtStartOfDay();
        
        if (!slotsByDate.containsKey(date)) {
            throw new RuntimeException("Don't have any timeslots on date - " + Constants.dateTimeformat.print(kickoff));
        }
        
        return slotsByDate.get(date).indexOf(kickoff);
    }
    
    public boolean isEmpty() {
        return slotsByDate.isEmpty();
    }

    @Override
    public String toString() {
        return "Timeslots [slotsByDate=" + slotsByDate + "]";
    }
}
