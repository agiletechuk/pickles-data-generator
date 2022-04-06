package uk.agiletech.pickles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleGeneratorTest {
    DoubleGenerator doubleGenerator;

    @Test
    public void test() {
        doubleGenerator = new DoubleGenerator(0.1d, 0.3d, 0.1d);
        assertEquals(0.1d,doubleGenerator.getCurrentValue());
        doubleGenerator.next();
        assertEquals(0.2d,doubleGenerator.getCurrentValue());
        doubleGenerator.next();
        assertEquals(null,doubleGenerator.getCurrentValue());
        assertTrue(doubleGenerator.end());
    }
}