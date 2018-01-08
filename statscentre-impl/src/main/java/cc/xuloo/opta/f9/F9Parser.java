package cc.xuloo.opta.f9;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F9Parser extends AbstractParser {

    public F9Parser() {
        super("f9");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F9.class);
        
        parser.registerConverter(new StatConverter());
        
        return super.setupParser(parser);
    }

}
