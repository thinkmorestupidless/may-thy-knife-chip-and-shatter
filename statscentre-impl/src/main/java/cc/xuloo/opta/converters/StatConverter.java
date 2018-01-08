package cc.xuloo.opta.converters;

import cc.xuloo.opta.Stat;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class StatConverter implements Converter {

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        Stat stat = (Stat) value;
        writer.addAttribute("Type", stat.type);
        writer.setValue(stat.value);
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Stat stat = new Stat();
        
        String attr = reader.getAttributeName(0);
        
        if ("Type".equals(attr)) {
            stat.type = reader.getAttribute("Type");
        } else if ("name".equals(attr)) {
            stat.type = reader.getAttribute("name");
        }
        
        stat.value = reader.getValue();
        return stat;
    }

    public boolean canConvert(Class clazz) {
        return clazz.equals(Stat.class);
    }

}