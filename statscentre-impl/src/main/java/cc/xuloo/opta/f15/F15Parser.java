package cc.xuloo.opta.f15;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F15Parser extends AbstractParser {

    public F15Parser() {
        super("f15");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F15.class);
        
        return super.setupParser(parser);
    }

}
