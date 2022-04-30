package uk.agiletech.pickles.format;

import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.data.IntegerData;
import uk.agiletech.pickles.data.LimitBehavior;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringFieldFormatTest {
    StringFormat stringFieldFormat;

    @Test
    public void testIntegerCurrency() {
        IntegerData gen = new IntegerData(1, 3, 1, LimitBehavior.NULL);
        stringFieldFormat = new StringFormat("£%d.00", List.of(gen));

        assertEquals("£1.00", stringFieldFormat.getValue());
        gen.next();
        assertEquals("£2.00", stringFieldFormat.getValue());
        gen.next();
        assertEquals("£3.00", stringFieldFormat.getValue());
        gen.next();
        assertEquals("£null.00", stringFieldFormat.getValue());
        assertTrue(gen.endSequence());
    }

    @Test
    public void testIntegerHexValues() {
        IntegerData gen = new IntegerData(9, 11, 1, LimitBehavior.NULL);
        stringFieldFormat = new StringFormat("0x%x", List.of(gen));

        assertEquals("0x9", stringFieldFormat.getValue());
        gen.next();
        assertEquals("0xa", stringFieldFormat.getValue());
        gen.next();
        assertEquals("0xb", stringFieldFormat.getValue());
        gen.next();
        assertTrue(gen.endSequence());
        assertEquals("0xnull", stringFieldFormat.getValue());
    }

    @Test
    public void testTwoGenerators() {
        IntegerData gen1 = new IntegerData(9, 11, 1, LimitBehavior.NULL);
        IntegerData gen2 = new IntegerData(0, 3, 2, LimitBehavior.LOOP);
        stringFieldFormat = new StringFormat("0x%x £%03d", List.of(gen1, gen2));

        assertEquals("0x9 £000", stringFieldFormat.getValue());
        gen1.next();
        gen2.next();
        assertEquals("0xa £002", stringFieldFormat.getValue());
        gen1.next();
        gen2.next();
        assertEquals("0xb £000", stringFieldFormat.getValue());
        gen1.next();
        gen2.next();
        assertTrue(gen1.endSequence());
        assertFalse(gen2.endSequence());
        assertEquals("0xnull £002", stringFieldFormat.getValue());
    }

}