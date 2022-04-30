package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;

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
        assertNull(doubleGenerator.getValue());
        assertTrue(doubleGenerator.endSequence());
    }
}