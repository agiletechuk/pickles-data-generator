package uk.agiletech.pickles.data;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
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
        for (int j = 0; j < 1000; j++) {
            assertFalse(t.endSequence());
            t.next();
            Object item = t.getValue();
            checkin(item, ITEMS);
        }

        assertEquals(1000, valueMap.get(ITEMS.get(0)) + valueMap.get(ITEMS.get(1)) + valueMap.get(ITEMS.get(2)));
        assertTrue(valueMap.get(ITEMS.get(0)) > 200);
        assertTrue(valueMap.get(ITEMS.get(1)) > 200);
        assertTrue(valueMap.get(ITEMS.get(2)) > 200);
    }

    @Test
    public void performanceTest() {
        List<Integer> items = ThreadLocalRandom.current().ints(1000).boxed().collect(Collectors.toList());
        ListValueData<Integer> t = new ListValueData<>(items, LimitBehavior.RANDOM);
        valueMap = new HashMap<>();

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
        System.out.format("%,d per second for ListValue Data\n", count);
        assertThat(count, greaterThan(1000000L));
    }

    Map<Object, Integer> valueMap = new HashMap<>();

    private void checkin(Object value, List<?> list) {
        assertNotNull(value);
        assertThat(list.contains(value), Matchers.is(true));
        int count = valueMap.getOrDefault(value, 0);
        valueMap.put(value, count + 1);
    }

}