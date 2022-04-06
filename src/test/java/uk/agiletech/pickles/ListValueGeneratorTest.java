package uk.agiletech.pickles;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ListValueGeneratorTest {
    List<Object> ITEMS = Arrays.asList("One", "Two", "Three");

    @Test
    public void sequenceTest() {
        ListValueGenerator<Object> t = new ListValueGenerator<>(ITEMS, LimitBehavior.NULL);
        assertEquals("One", t.getCurrentValue());
        assertFalse(t.end());
        t.next();
        assertEquals("Two", t.getCurrentValue());
        assertFalse(t.end());
        t.next();
        assertEquals("Three", t.getCurrentValue());
        assertFalse(t.end());
        t.next();
        assertNull(t.getCurrentValue());
        assertTrue(t.end());
    }

    @Test
    public void loopTest() {
        ListValueGenerator<Object> t = new ListValueGenerator<>(ITEMS, LimitBehavior.LOOP);
        assertEquals("One", t.getCurrentValue());
        assertFalse(t.end());
        t.next();
        assertEquals("Two", t.getCurrentValue());
        assertFalse(t.end());
        t.next();
        assertEquals("Three", t.getCurrentValue());
        assertFalse(t.end());
        t.next();
        assertEquals("One", t.getCurrentValue());
    }

    @Test
    public void randomTest() {
        valueMap = new HashMap<Object, Integer>();
        ListValueGenerator<Object> t = new ListValueGenerator<>(ITEMS, LimitBehavior.RANDOM);
        for (int j = 0; j < 100; j++) {
            assertFalse(t.end());
            t.next();
            Object item = t.getCurrentValue();
            checkin(item, ITEMS);
        }

        assertEquals(100, valueMap.get(ITEMS.get(0)) + valueMap.get(ITEMS.get(1)) + valueMap.get(ITEMS.get(2)));
        assertTrue(valueMap.get(ITEMS.get(0)) > 20);
        assertTrue(valueMap.get(ITEMS.get(1)) > 20);
        assertTrue(valueMap.get(ITEMS.get(2)) > 20);
    }

    @Test
    public void performanceTest() {
        List<Integer> items = ThreadLocalRandom.current().ints(1000).boxed().collect(Collectors.toList());
        ListValueGenerator<Integer> t = new ListValueGenerator<>(items, LimitBehavior.RANDOM);
        valueMap = new HashMap<Object, Integer>();

        long end = System.currentTimeMillis() + 1000;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            Integer item = t.getCurrentValue();
            checkin(item, items);
            if (count < 5) System.out.println("Item: " + item);
            count++;
            assertFalse(t.end());
            t.next();
        }
        System.out.format("%,d messages in one second\n", count);
        assertTrue(count > 2000000);
    }

    Map<Object, Integer> valueMap = new HashMap<Object, Integer>();

    private void checkin(Object value, List<?> list) {
        assertNotNull(value);
        list.contains(value);
        int count = valueMap.getOrDefault(value, 0);
        valueMap.put(value, count + 1);
    }

}