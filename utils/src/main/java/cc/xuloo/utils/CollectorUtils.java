package cc.xuloo.utils;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collector;


public class CollectorUtils {
    
    public static <T> Collector<T, ?, List<T>> toSortedList() {
        return collectingAndThen(toList(), l -> l.stream().sorted().collect(toList()));
    }

    public static <T> Collector<T, ?, List<T>> toDistinctSortedList() {
        return collectingAndThen(toList(), l -> l.stream().distinct().sorted().collect(toList()));
    }
    
    
}
