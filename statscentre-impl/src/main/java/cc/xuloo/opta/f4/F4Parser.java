package cc.xuloo.opta.f4;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F4Parser extends AbstractParser {

    public F4Parser() {
        super("f4");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F4.class);
        
        return super.setupParser(parser);
    }

}
