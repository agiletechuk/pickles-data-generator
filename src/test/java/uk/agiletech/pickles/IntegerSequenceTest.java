package uk.agiletech.pickles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerSequenceTest {

    IntegerSequence integerSequence;

    @Test
    public void typicalAscending() {
        integerSequence = new IntegerSequence(0, 3, 1);
        assertEquals(0, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(1, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(2, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(3, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertNull(integerSequence.getCurrentValue());
        assertFalse(integerSequence.hasNext());
    }


    @Test
    public void typicalDescending() {
        integerSequence = new IntegerSequence(10, 8, -1);
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(9, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(8, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertNull(integerSequence.getCurrentValue());
        assertFalse(integerSequence.hasNext());
    }

    @Test
    public void inccrementMoreThanOneDescending() {
        integerSequence = new IntegerSequence(10, 8, -2);
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(8, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertNull(integerSequence.getCurrentValue());
        assertFalse(integerSequence.hasNext());
    }

    @Test
    public void inccrementMoreThanOneAscending() {
        integerSequence = new IntegerSequence(8, 10, 2);
        assertTrue(integerSequence.hasNext());
        assertEquals(8, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertNull(integerSequence.getCurrentValue());
        assertFalse(integerSequence.hasNext());
    }

    @Test
    public void inccrementTakesPastEndDescending() {
        integerSequence = new IntegerSequence(14, 8, -4);
        assertEquals(14, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertNull(integerSequence.getCurrentValue());
        assertFalse(integerSequence.hasNext());
    }

    @Test
    public void illegalArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            integerSequence = new IntegerSequence(10, 8, 1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            integerSequence = new IntegerSequence(5, 10, -1);
        });
    }

    /*
     * Loop
     */
    @Test
    public void typicalAscendingWithLoop() {
        integerSequence = new IntegerSequence(0, 3, 1, LimitBehavior.LOOP);
        assertEquals(0, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(1, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(2, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(3, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(0, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(1, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(2, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(3, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(0, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(1, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
    }

    @Test
    public void typicalDescendingWithLoop() {
        integerSequence = new IntegerSequence(10, 8, -1, LimitBehavior.LOOP);
        assertTrue(integerSequence.hasNext());
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(9, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(8, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(9, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(8, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(10, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(9, integerSequence.getCurrentValue());
        integerSequence.next();
        assertTrue(integerSequence.hasNext());
    }

    @Test
    public void inccrementMoreThanOneDescendingWithLoop() {
        integerSequence = new IntegerSequence(10, 8, -2, LimitBehavior.LOOP);
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(8, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(9, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(10, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(8, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
    }

    @Test
    public void inccrementTakesPastEndDescendingWithLoop() {
        integerSequence = new IntegerSequence(14, 8, -4, LimitBehavior.LOOP);
        assertEquals(14, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(10, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
        integerSequence.next();
        assertEquals(13, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(9, integerSequence.getCurrentValue());
        integerSequence.next();
        assertEquals(12, integerSequence.getCurrentValue());
        assertTrue(integerSequence.hasNext());
    }

}