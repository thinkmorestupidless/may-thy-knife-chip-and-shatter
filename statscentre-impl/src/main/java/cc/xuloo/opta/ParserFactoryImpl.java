package cc.xuloo.opta;

import cc.xuloo.opta.f1.F1Parser;
import cc.xuloo.opta.f13.F13Parser;
import cc.xuloo.opta.f15.F15Parser;
import cc.xuloo.opta.f3.F3Parser;
import cc.xuloo.opta.f30.F30Parser;
import cc.xuloo.opta.f4.F4Parser;
import cc.xuloo.opta.f40.F40Parser;
import cc.xuloo.opta.f9.F9Parser;

import java.util.HashSet;
import java.util.Set;

public class ParserFactoryImpl implements ParserFactory {
    
    final private Set<Parser> parsers;

    public ParserFactoryImpl() {
        parsers = new HashSet<>();
        
        parsers.add(new F1Parser());
        parsers.add(new F3Parser());
        parsers.add(new F4Parser());
        parsers.add(new F9Parser());
        parsers.add(new F13Parser());
        parsers.add(new F15Parser());
        parsers.add(new F30Parser());
        parsers.add(new F40Parser());
    }
    
    /* (non-Javadoc)
     * @see com.flip.domain.ParserFactory#forFeedType(java.lang.String)
     */
    @Override
    public Parser forFeedType(String feedType) {
        return parsers.stream()
                      .filter(parser -> parser.respondsTo(feedType))
                      .findFirst()
                      .orElseGet(() -> AbstractParser.none());
    }
}
