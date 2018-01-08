package cc.xuloo.opta.f30;

import cc.xuloo.opta.AbstractParser;
import com.thoughtworks.xstream.XStream;

public class F30Parser extends AbstractParser {

    public F30Parser() {
        super("f30");
    }
    
    @Override
    protected XStream setupParser(XStream parser) {
        parser.processAnnotations(F30.class);
        
        return super.setupParser(parser);
    }

}
