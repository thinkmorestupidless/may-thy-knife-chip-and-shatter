package cc.xuloo.opta.f1;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F1Parser extends AbstractParser {
    
    public F1Parser() {
        super("f1");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F1.class);
        
        return super.setupParser(parser);
    }
}
