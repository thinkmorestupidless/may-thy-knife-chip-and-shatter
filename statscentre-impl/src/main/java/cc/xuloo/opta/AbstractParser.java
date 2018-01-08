package cc.xuloo.opta;

import cc.xuloo.opta.SportData;
import cc.xuloo.opta.converters.StatConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.Map;

public abstract class AbstractParser implements Parser {
    
    final private String[] feedTypes;
    
    private XStream parser;
    
    protected AbstractParser(String feedType) {
        this(new String[]{feedType});
    }
    
    protected AbstractParser(String[] feedTypes) {
        this.feedTypes = feedTypes;
    }

    protected XStream setupParser(XStream parser) {
        parser.registerConverter(new DateConverter("yyyyMMdd'T'HHmmssZ", new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy"}));
        parser.registerConverter(new StatConverter());
        
        return parser;
    }
    
    protected XStream parser() {
        if (parser == null) {
            parser = setupParser(new XStream(new DomDriver("UTF-8", new NoNameCoder())));
        }
        
        return parser;
    }
    
    public boolean respondsTo(String feedType) {
        for (String s : feedTypes) {
            if (s.equals(feedType.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public SportData parse(String input, Map<String, Object> headers) {
        SportData data = (SportData)parser().fromXML(input);
        data.setHeaders(headers);
        
        return data;
    }
    
    public static Parser none() {
        return NullParser.instance();
    }
    
    private static class NullParser implements Parser {
        
        private static Parser instance;
        
        public static Parser instance() {
            return instance = instance == null ? new NullParser() : instance;
        }

        @Override
        public SportData parse(String input, Map<String, Object> headers) {
            return SportData.none();
        }

        @Override
        public boolean respondsTo(String feedType) {
            return false;
        }
        
    }
}
