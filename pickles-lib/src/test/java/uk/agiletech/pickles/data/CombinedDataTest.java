package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CombinedDataTest {
    @Test
    public void combineTwoData() {
        IntegerData intData = new IntegerData(1, 3, 1);
        DoubleData doubleData = new DoubleData(0.01, 0.03, 0.01);
        List<Data<?>> data = List.of(intData, doubleData);
        CombinedData combinedData = new CombinedData(data, LimitBehavior.NULL);
        assertThat(combinedData.getValue()).isEqualTo(1);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(0.01);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(2);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(0.02);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(3);
        combinedData.next();
        assertThat(combinedData.endSequence()).isFalse();
        assertThat(combinedData.getValue()).isEqualTo(0.03);
        combinedData.next();
        // Will end when any of the data sets ends
        assertThat(combinedData.endSequence()).isTrue();
    }

    @Test
    public void combineTwoDataWithLoop() {
        IntegerData intData = new IntegerData(1, 2, 1);
        DoubleData doubleData = new DoubleData(0.01, 0.03, 0.01);
        List<Data<?>> data = List.of(intData, doubleData);
        CombinedData combinedData = new CombinedData(data, LimitBehavior.LOOP);
        assertThat(combinedData.getValue()).isEqualTo(1);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(0.01);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(2);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(0.02);
        combinedData.next();
        assertThat(combinedData.getValue()).isEqualTo(1);
        // Will start from begning when an end is encoutered in any data when LOOP behavior is specified
        assertThat(combinedData.endSequence()).isFalse();
    }

    @Test
    public void combineTwoDataWithRandom() {
        IntegerData intData = new IntegerData(1, 2, 1);
        DoubleData doubleData = new DoubleData(0.01, 0.02, 0.01);
        List<Data<?>> data = List.of(doubleData, intData);
        CombinedData combinedData = new CombinedData(data, LimitBehavior.RANDOM);
        Set<Number> bag = new HashSet<>();
        for (int i=0; i<20; i++) {
            bag.add((Number) combinedData.getValue());
            combinedData.next();
        }
        assertThat(combinedData.endSequence()).isFalse();
        assertThat(bag).containsAll(List.of(1,2,0.01,0.02));
    }


}