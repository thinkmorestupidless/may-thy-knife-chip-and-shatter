package cc.xuloo.opta.f9;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class PeriodData implements Serializable {
    
    private String key;
    
    private Number[] periods;
    
    private Number overall;
    
    public PeriodData() {
        key = StringUtils.EMPTY;
        periods = new Number[]{};
        overall = 0;
    }

    public PeriodData(String key, Number[] periods, Number overall) {
        this.key = key;
        this.periods = periods;
        this.overall = overall;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Number[] getPeriods() {
        return periods;
    }

    public void setPeriods(Number[] periods) {
        this.periods = periods;
    }

    public Number getOverall() {
        return overall != null ? overall : BigDecimal.ZERO;
    }

    public void setOverall(Number overall) {
        this.overall = overall;
    }

    @Override
    public String toString() {
        return "PeriodData [key=" + key + ", periods=" + Arrays.toString(periods) + ", overall=" + overall
               + "]";
    }
}
