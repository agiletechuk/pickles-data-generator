package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

class LongDataTest {

    LongData longGenerator;

    @Test
    public void typicalAscending() {
        longGenerator = new LongData(0, 3, 1);
        assertEquals(0, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(1, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(2, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(3, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertNull(longGenerator.getValue());
        assertTrue(longGenerator.endSequence());
    }


    @Test
    public void typicalDescending() {
        longGenerator = new LongData(10, 8, -1);
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(9, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(8, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertNull(longGenerator.getValue());
        assertTrue(longGenerator.endSequence());
    }

    @Test
    public void inccrementMoreThanOneDescending() {
        longGenerator = new LongData(10, 8, -2);
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(8, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertNull(longGenerator.getValue());
        assertTrue(longGenerator.endSequence());
    }

    @Test
    public void inccrementMoreThanOneAscending() {
        longGenerator = new LongData(8, 10, 2);
        assertFalse(longGenerator.endSequence());
        assertEquals(8, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertNull(longGenerator.getValue());
        assertTrue(longGenerator.endSequence());
    }

    @Test
    public void inccrementTakesPastEndDescending() {
        longGenerator = new LongData(14, 8, -4);
        assertEquals(14, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertNull(longGenerator.getValue());
        assertTrue(longGenerator.endSequence());
    }

    @Test
    public void illegalArguments() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> longGenerator = new LongData(10, 8, 1));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> longGenerator = new LongData(5, 10, -1));
    }

    /*
     * Loop
     */
    @Test
    public void typicalAscendingWithLoop() {
        longGenerator = new LongData(0, 3, 1, LimitBehavior.LOOP);
        assertEquals(0, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(1, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(2, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(3, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(0, longGenerator.getValue());
        longGenerator.next();
        assertEquals(1, longGenerator.getValue());
        longGenerator.next();
        assertEquals(2, longGenerator.getValue());
        longGenerator.next();
        assertEquals(3, longGenerator.getValue());
        longGenerator.next();
        assertEquals(0, longGenerator.getValue());
        longGenerator.next();
        assertEquals(1, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
    }

    @Test
    public void typicalDescendingWithLoop() {
        longGenerator = new LongData(10, 8, -1, LimitBehavior.LOOP);
        assertFalse(longGenerator.endSequence());
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(9, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(8, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(9, longGenerator.getValue());
        longGenerator.next();
        assertEquals(8, longGenerator.getValue());
        longGenerator.next();
        assertEquals(10, longGenerator.getValue());
        longGenerator.next();
        assertEquals(9, longGenerator.getValue());
        longGenerator.next();
        assertFalse(longGenerator.endSequence());
    }

    @Test
    public void inccrementMoreThanOneDescendingWithLoop() {
        longGenerator = new LongData(10, 8, -2, LimitBehavior.LOOP);
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(8, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(9, longGenerator.getValue());
        longGenerator.next();
        assertEquals(10, longGenerator.getValue());
        longGenerator.next();
        assertEquals(8, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
    }

    @Test
    public void inccrementTakesPastEndDescendingWithLoop() {
        longGenerator = new LongData(14, 8, -4, LimitBehavior.LOOP);
        assertEquals(14, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(10, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
        longGenerator.next();
        assertEquals(13, longGenerator.getValue());
        longGenerator.next();
        assertEquals(9, longGenerator.getValue());
        longGenerator.next();
        assertEquals(12, longGenerator.getValue());
        assertFalse(longGenerator.endSequence());
    }

    @Test
    public void performance() {
        LongData data = new LongData(Long.MIN_VALUE, Long.MAX_VALUE,1, LimitBehavior.NULL);
        long start = System.currentTimeMillis();
        long count = 0;
        do {
            long val = data.getLongValue();
            data.next();
            if (count<5) System.out.println(val);
            count ++;
        } while(System.currentTimeMillis() - start < 1000);
        System.out.printf("%,d messages a second Performance of long data%n",count);
        assertThat(count, greaterThan(1000000L));
    }

}