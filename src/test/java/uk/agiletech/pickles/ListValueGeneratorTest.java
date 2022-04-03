package uk.agiletech.pickles;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ListValueGeneratorTest {
    List<Object> ITEMS = Arrays.asList("One", "Two", "Three");

    @Test
    public void randomTest() {
        ListValueGenerator<Object> t = new ListValueGenerator<Object>(ITEMS, Behavior.RANDOM);
        for (int j = 0; j < 10; j++) {
            assertTrue(t.hasNext());
            Object item = t.next();
            checkin(item, ITEMS);
        }
        assertEquals(3,valueSet.size());
        assertTrue(valueSet.containsAll(ITEMS));
    }

    @Test
    public void performanceTest() {
        List<Integer> items = ThreadLocalRandom.current().ints(1000).boxed().collect(Collectors.toList());
        ListValueGenerator<Integer> t = new ListValueGenerator<Integer>( items, Behavior.LOOP);

        long end = System.currentTimeMillis() + 1000;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            assertTrue(t.hasNext());
            Integer item = t.next();
            valueSet.add(item);
            if (count < 5) System.out.println("Item: "+item);
            count ++;
        }
        System.out.format("%,d messages in one second\n", count);
        assertTrue(count > 40000000);
        assertTrue(valueSet.containsAll(valueSet));
    }

    Set<Object> valueSet = new HashSet<>();

    private void checkin(Object value, List<Object> list) {
        assertNotNull(value);
        list.contains(value);
        valueSet.add(value);
    }

}