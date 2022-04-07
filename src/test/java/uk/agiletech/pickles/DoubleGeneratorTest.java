package uk.agiletech.pickles;

import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.data.DoubleData;

import static org.junit.jupiter.api.Assertions.*;

class DoubleGeneratorTest {
    DoubleData doubleGenerator;

    @Test
    public void test() {
        doubleGenerator = new DoubleData(0.1d, 0.3d, 0.1d);
        assertEquals(0.1d,doubleGenerator.getValue());
        doubleGenerator.next();
        assertEquals(0.2d,doubleGenerator.getValue());
        doubleGenerator.next();
        assertEquals(null,doubleGenerator.getValue());
        assertTrue(doubleGenerator.endSequence());
    }
}