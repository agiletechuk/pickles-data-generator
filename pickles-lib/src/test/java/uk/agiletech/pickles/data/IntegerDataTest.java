package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

class IntegerDataTest {

    IntegerData integerGenerator;

    @Test
    public void typicalAscending() {
        integerGenerator = new IntegerData(0, 3, 1);
        assertEquals(0, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
    }


    @Test
    public void typicalAscendingWithResetMidway() {
        integerGenerator = new IntegerData(0, 3, 1);
        assertEquals(0, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.reset();
        assertEquals(0, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
    }

    @Test
    public void typicalAscendingWithResetAtEnd() {
        integerGenerator = new IntegerData(0, 3, 1);
        assertEquals(0, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
        integerGenerator.reset();
        assertEquals(0, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
    }


    @Test
    public void typicalDescending() {
        integerGenerator = new IntegerData(10, 8, -1);
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
    }

    @Test
    public void inccrementMoreThanOneDescending() {
        integerGenerator = new IntegerData(10, 8, -2);
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
    }

    @Test
    public void inccrementMoreThanOneAscending() {
        integerGenerator = new IntegerData(8, 10, 2);
        assertFalse(integerGenerator.endSequence());
        assertEquals(8, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
    }

    @Test
    public void inccrementTakesPastEndDescending() {
        integerGenerator = new IntegerData(14, 8, -4);
        assertEquals(14, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertNull(integerGenerator.getValue());
        assertTrue(integerGenerator.endSequence());
    }

    @Test
    public void illegalArguments() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> integerGenerator = new IntegerData(10, 8, 1));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> integerGenerator = new IntegerData(5, 10, -1));
    }

    /*
     * Loop
     */
    @Test
    public void typicalAscendingWithLoop() {
        integerGenerator = new IntegerData(0, 3, 1, LimitBehavior.LOOP);
        assertEquals(0, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(0, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(2, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(3, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(0, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(1, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
    }

    @Test
    public void typicalDescendingWithLoop() {
        integerGenerator = new IntegerData(10, 8, -1, LimitBehavior.LOOP);
        assertFalse(integerGenerator.endSequence());
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getValue());
        integerGenerator.next();
        assertFalse(integerGenerator.endSequence());
    }

    @Test
    public void inccrementMoreThanOneDescendingWithLoop() {
        integerGenerator = new IntegerData(10, 8, -2, LimitBehavior.LOOP);
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(8, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
    }

    @Test
    public void inccrementTakesPastEndDescendingWithLoop() {
        integerGenerator = new IntegerData(14, 8, -4, LimitBehavior.LOOP);
        assertEquals(14, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(10, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
        integerGenerator.next();
        assertEquals(13, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(9, integerGenerator.getValue());
        integerGenerator.next();
        assertEquals(12, integerGenerator.getValue());
        assertFalse(integerGenerator.endSequence());
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
        IntegerData data = new IntegerData(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, limitBehavior);
        long start = System.currentTimeMillis();
        long count = 0;
        do {
            int val = data.getIntValue();
            data.next();
            if (count < 5) System.out.println(val);
            count++;
        } while (System.currentTimeMillis() - start < 1000);
        System.out.printf("%,d per second Performance of %s integer data%n", count,
                limitBehavior == LimitBehavior.RANDOM ? "random" : "sequential");
        assertThat(count, greaterThan(1000000L));
    }

}