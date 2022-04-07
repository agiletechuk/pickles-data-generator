package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.data.LimitBehavior;
import uk.agiletech.pickles.data.ListValueData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ListValueDataTest {
    List<Object> ITEMS = Arrays.asList("One", "Two", "Three");

    @Test
    public void sequenceTest() {
        ListValueData<Object> t = new ListValueData<>(ITEMS, LimitBehavior.NULL);
        assertEquals("One", t.getValue());
        assertFalse(t.endSequence());
        t.next();
        assertEquals("Two", t.getValue());
        assertFalse(t.endSequence());
        t.next();
        assertEquals("Three", t.getValue());
        assertFalse(t.endSequence());
        t.next();
        assertNull(t.getValue());
        assertTrue(t.endSequence());
    }

    @Test
    public void loopTest() {
        ListValueData<Object> t = new ListValueData<>(ITEMS, LimitBehavior.LOOP);
        assertEquals("One", t.getValue());
        assertFalse(t.endSequence());
        t.next();
        assertEquals("Two", t.getValue());
        assertFalse(t.endSequence());
        t.next();
        assertEquals("Three", t.getValue());
        assertFalse(t.endSequence());
        t.next();
        assertEquals("One", t.getValue());
    }

    @Test
    public void randomTest() {
        valueMap = new HashMap<>();
        ListValueData<Object> t = new ListValueData<>(ITEMS, LimitBehavior.RANDOM);
        for (int j = 0; j < 100; j++) {
            assertFalse(t.endSequence());
            t.next();
            Object item = t.getValue();
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
        ListValueData<Integer> t = new ListValueData<>(items, LimitBehavior.RANDOM);
        valueMap = new HashMap<Object, Integer>();

        long end = System.currentTimeMillis() + 1000;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            Integer item = t.getValue();
            checkin(item, items);
            if (count < 5) System.out.println("Item: " + item);
            count++;
            assertFalse(t.endSequence());
            t.next();
        }
        System.out.format("%,d messages in one second\n", count);
        assertTrue(count > 1000000);
    }

    Map<Object, Integer> valueMap = new HashMap<Object, Integer>();

    private void checkin(Object value, List<?> list) {
        assertNotNull(value);
        list.contains(value);
        int count = valueMap.getOrDefault(value, 0);
        valueMap.put(value, count + 1);
    }

}