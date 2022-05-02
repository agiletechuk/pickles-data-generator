package uk.agiletech.pickles.data;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DoubleDataTest {

    @Test
    public void typical() {
        DoubleData doubleData = new DoubleData(1.000001, 5.000005, 1.000001, LimitBehavior.NULL);
        assertThat(doubleData.getValue(), closeTo(1.000001, 0.000000000001));
        doubleData.next();
        assertThat(doubleData.getValue(), closeTo(2.000002, 0.000000000001));
        doubleData.next();
        assertThat(doubleData.getValue(), closeTo(3.000003, 0.000000000001));
        doubleData.next();
        assertThat(doubleData.getValue(), closeTo(4.000004, 0.000000000001));
        doubleData.next();
        assertThat(doubleData.getValue(), closeTo(5.000005, 0.000000000001));
        doubleData.next();
        assertThat(doubleData.getValue(), nullValue());
        doubleData.next();
        assertThat(doubleData.getValue(), nullValue());
    }




    @Test
    public void sequentialPerformance() {
        performance(LimitBehavior.NULL);
    }


    @Test
    public void randomPerformance() {
        performance(LimitBehavior.RANDOM);
    }

    private void performance(LimitBehavior limitBehavior) {
        DoubleData data = new DoubleData(Double.MIN_VALUE, Double.MAX_VALUE,1.1, limitBehavior);
        long start = System.currentTimeMillis();
        long count = 0;
        do {
            double val = data.getDoubleValue();
            data.next();
            if (count<5) System.out.println(val);
            count ++;
        } while(System.currentTimeMillis() - start < 1000);
        System.out.printf("%,d per second Performance of %s double data%n",count,
                limitBehavior == LimitBehavior.RANDOM ? "random" : "sequential");
        assertThat(count, greaterThan(1000000L));
    }

}