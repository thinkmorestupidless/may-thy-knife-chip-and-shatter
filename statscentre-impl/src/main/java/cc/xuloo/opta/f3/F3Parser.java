package cc.xuloo.opta.f3;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F3Parser extends AbstractParser {

    public F3Parser() {
        super("f3");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F3.class);
        
        return super.setupParser(parser);
    }

}
