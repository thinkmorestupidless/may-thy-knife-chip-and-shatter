package cc.xuloo.betfair.client.stream;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class RunnerChangeTest {

    @Test
    public void testMergeLists() {
        List<List<Double>> a = Lists.newArrayList(
                Lists.newArrayList(0.0, 1.01, 10.0),
                Lists.newArrayList(1.0, 1.02, 10.0),
                Lists.newArrayList(2.0, 2.0, 10.0),
                Lists.newArrayList(3.0, 2.02, 10.0),
                Lists.newArrayList(4.0, 2.04, 10.0),
                Lists.newArrayList(5.0, 3.02, 10.0),
                Lists.newArrayList(6.0, 3.04, 10.0),
                Lists.newArrayList(7.0, 4.0, 10.0),
                Lists.newArrayList(8.0, 4.05, 10.0),
                Lists.newArrayList(9.0, 5.0, 10.0)
        );

        List<List<Double>> b = Lists.newArrayList(
                Lists.newArrayList(1.0, 1.02, 20.0),
                Lists.newArrayList(5.0, 3.02, 100.0),
                Lists.newArrayList(9.0, 2.0, 0.0)
        );

        RunnerChange rc = RunnerChange.empty();

        assertThat(rc.merge(a, b, 2))
                .hasSize(9)
                .contains(Lists.newArrayList(0.0, 1.01, 10.0),
                        Lists.newArrayList(1.0, 1.02, 20.0),
                        Lists.newArrayList(2.0, 2.0, 10.0),
                        Lists.newArrayList(3.0, 2.02, 10.0),
                        Lists.newArrayList(4.0, 2.04, 10.0),
                        Lists.newArrayList(5.0, 3.02, 100.0),
                        Lists.newArrayList(6.0, 3.04, 10.0),
                        Lists.newArrayList(7.0, 4.0, 10.0),
                        Lists.newArrayList(8.0, 4.05, 10.0));
    }
}
