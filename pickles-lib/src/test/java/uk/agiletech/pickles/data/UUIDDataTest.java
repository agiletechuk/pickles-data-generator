package uk.agiletech.pickles.data;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDDataTest {

    private final UUIDData sut = new UUIDData();

    @Test
    public void testType() {
        assertInstanceOf(UUID.class, sut.getValue());
    }

    @Test
    public void testNonNull() {
        assertNotNull(sut.getValue());
    }

    @Test
    public void testConsecutiveGetValuesSame() {
        UUID v1 = sut.getValue();
        UUID v2 = sut.getValue();
        assertEquals(v1,v2);
        assertNotNull(v1);
        assertNotNull(v2);
    }

    @Test
    public void testConsecutiveGetValuesDifferentAfterNext() {
        UUID v1 = sut.getValue();
        sut.next();
        UUID v2 = sut.getValue();
        assertNotEquals(v1,v2);
        assertNotNull(v1);
        assertNotNull(v2);
    }
}