package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.PicklesException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorGroupTest {
    IntegerData ints = new IntegerData(1, 3, 1);
    ListValueData<String> colours = new ListValueData<String>(List.of("Red", "Green", "Blue"), LimitBehavior.NULL);

    @Test
    public void test() {
        GeneratorGroup group = new GeneratorGroup(ints, colours);

        assertEquals(1,ints.getValue());
        assertEquals("Red",colours.getValue());
        group.next();
        assertEquals(2,ints.getValue());
        assertEquals("Red",colours.getValue());
        group.next();
        assertEquals(3,ints.getValue());
        assertEquals("Red",colours.getValue());
        group.next();
        assertEquals(1,ints.getValue());
        assertEquals("Green",colours.getValue());
        group.next();
        group.next();
        group.next();
        assertEquals(1,ints.getValue());
        assertEquals("Blue",colours.getValue());
        group.next();
        group.next();
        assertEquals(3,ints.getValue());
        assertEquals("Blue",colours.getValue());
        assertFalse(group.endSequence());
        group.next();
        assertTrue(group.endSequence());
        assertNull(colours.getValue());
        assertNull(ints.getValue());
    }

    @Test
    public void groupMustHaveMoreThanOneGeneraator() {
        assertThrows(PicklesException.class, () -> new GeneratorGroup(ints));
    }

    @Test
    public void nonGroupableGenerators() {
        IntegerData nonGroupable = new IntegerData(1, 3, 1, LimitBehavior.REPEAT);
        assertThrows(PicklesException.class, () -> new GeneratorGroup(nonGroupable, colours));
    }

    @Test
    public void reset() {
        GeneratorGroup group = new GeneratorGroup(ints, colours);

        assertEquals(1,ints.getValue());
        assertEquals("Red",colours.getValue());
        group.next();
        group.next();
        group.next();
        group.next();
        assertEquals(2,ints.getValue());
        assertEquals("Green",colours.getValue());
        group.reset();
        assertEquals(1,ints.getValue());
        assertEquals("Red",colours.getValue());
        assertFalse(group.endSequence());
    }

}