package cc.xuloo.opta.f40;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F40Parser extends AbstractParser {

    public F40Parser() {
        super("f40");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F40.class);
        
        return super.setupParser(parser);
    }
}
