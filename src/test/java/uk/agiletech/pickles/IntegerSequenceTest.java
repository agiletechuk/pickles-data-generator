package uk.agiletech.pickles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerSequenceTest {

    IntegerSequence integerSequence;

    @Test
    public void typicalAscending() {
        integerSequence = new IntegerSequence(0, 3, 1);
        assertTrue(integerSequence.hasNext());
        assertEquals(0, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(1, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(2, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(3, integerSequence.next());
        assertFalse(integerSequence.hasNext());
        assertNull(integerSequence.next());
    }


    @Test
    public void typicalDescending() {
        integerSequence = new IntegerSequence(10, 8, -1);
        assertTrue(integerSequence.hasNext());
        assertEquals(10, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(9, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(8, integerSequence.next());
        assertFalse(integerSequence.hasNext());
        assertNull(integerSequence.next());
    }

    @Test
    public void inccrementMoreThanOneDescending() {
        integerSequence = new IntegerSequence(10, 8, -2);
        assertTrue(integerSequence.hasNext());
        assertEquals(10, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(8, integerSequence.next());
        assertFalse(integerSequence.hasNext());
        assertNull(integerSequence.next());
    }

    @Test
    public void inccrementMoreThanOneAscending() {
        integerSequence = new IntegerSequence(8, 10, 2);
        assertTrue(integerSequence.hasNext());
        assertEquals(8, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(10, integerSequence.next());
        assertFalse(integerSequence.hasNext());
        assertNull(integerSequence.next());
    }

    @Test
    public void inccrementTakesPastEndDescending() {
        integerSequence = new IntegerSequence(14, 8, -4);
        assertTrue(integerSequence.hasNext());
        assertEquals(14, integerSequence.next());
        assertTrue(integerSequence.hasNext());
        assertEquals(10, integerSequence.next());
        assertFalse(integerSequence.hasNext());
        assertNull(integerSequence.next());
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

}