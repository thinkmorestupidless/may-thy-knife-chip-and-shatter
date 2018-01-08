package cc.xuloo.opta.f13;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F13Parser extends AbstractParser {

    public F13Parser() {
        super("f13");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F13.class);
        
        return super.setupParser(parser);
    }

}
