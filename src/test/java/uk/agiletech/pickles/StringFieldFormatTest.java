package uk.agiletech.pickles;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringFieldFormatTest {
    StringFieldFormat stringFieldFormat;

    @Test
    public void testIntegerCurrency() {
        IntegerGenerator gen = new IntegerGenerator(1, 3, 1, LimitBehavior.NULL);
        stringFieldFormat = new StringFieldFormat("£%d.00", List.of(gen));

        assertEquals("£1.00", stringFieldFormat.getValue());
        gen.next();
        assertEquals("£2.00", stringFieldFormat.getValue());
        gen.next();
        assertEquals("£3.00", stringFieldFormat.getValue());
        gen.next();
        assertEquals("£null.00", stringFieldFormat.getValue());
        assertTrue(gen.end());
    }

    @Test
    public void testIntegerHexValues() {
        IntegerGenerator gen = new IntegerGenerator(9, 11, 1, LimitBehavior.NULL);
        stringFieldFormat = new StringFieldFormat("0x%x", List.of(gen));

        assertEquals("0x9", stringFieldFormat.getValue());
        gen.next();
        assertEquals("0xa", stringFieldFormat.getValue());
        gen.next();
        assertEquals("0xb", stringFieldFormat.getValue());
        gen.next();
        assertTrue(gen.end());
        assertEquals("0xnull", stringFieldFormat.getValue());
    }



}