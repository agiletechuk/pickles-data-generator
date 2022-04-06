package uk.agiletech.pickles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerSequenceTest {

    IntegerGenerator integerGenerator;

    @Test
    public void typicalAscending() {
        integerGenerator = new IntegerGenerator(0, 3, 1);
        assertEquals(0, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertNull(integerGenerator.getCurrentValue());
        assertTrue(integerGenerator.end());
    }


    @Test
    public void typicalDescending() {
        integerGenerator = new IntegerGenerator(10, 8, -1);
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertNull(integerGenerator.getCurrentValue());
        assertTrue(integerGenerator.end());
    }

    @Test
    public void inccrementMoreThanOneDescending() {
        integerGenerator = new IntegerGenerator(10, 8, -2);
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertNull(integerGenerator.getCurrentValue());
        assertTrue(integerGenerator.end());
    }

    @Test
    public void inccrementMoreThanOneAscending() {
        integerGenerator = new IntegerGenerator(8, 10, 2);
        assertFalse(integerGenerator.end());
        assertEquals(8, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertNull(integerGenerator.getCurrentValue());
        assertTrue(integerGenerator.end());
    }

    @Test
    public void inccrementTakesPastEndDescending() {
        integerGenerator = new IntegerGenerator(14, 8, -4);
        assertEquals(14, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertNull(integerGenerator.getCurrentValue());
        assertTrue(integerGenerator.end());
    }

    @Test
    public void illegalArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            integerGenerator = new IntegerGenerator(10, 8, 1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            integerGenerator = new IntegerGenerator(5, 10, -1);
        });
    }

    /*
     * Loop
     */
    @Test
    public void typicalAscendingWithLoop() {
        integerGenerator = new IntegerGenerator(0, 3, 1, LimitBehavior.LOOP);
        assertEquals(0, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(0, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(0, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
    }

    @Test
    public void typicalDescendingWithLoop() {
        integerGenerator = new IntegerGenerator(10, 8, -1, LimitBehavior.LOOP);
        assertFalse(integerGenerator.end());
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertFalse(integerGenerator.end());
    }

    @Test
    public void inccrementMoreThanOneDescendingWithLoop() {
        integerGenerator = new IntegerGenerator(10, 8, -2, LimitBehavior.LOOP);
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
    }

    @Test
    public void inccrementTakesPastEndDescendingWithLoop() {
        integerGenerator = new IntegerGenerator(14, 8, -4, LimitBehavior.LOOP);
        assertEquals(14, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
        integerGenerator.next();
        assertEquals(13, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getCurrentValue());
        integerGenerator.next();
        assertEquals(12, integerGenerator.getCurrentValue());
        assertFalse(integerGenerator.end());
    }

}