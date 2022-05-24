package uk.agiletech.pickles.data;

import net.bytebuddy.asm.Advice;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;

class DateTimeDataTest {

    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

    @Test
    public void durationDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        LocalDateTime start = LocalDateTime.of(1971, Month.MARCH, 30, 11, 30);
        LocalDateTime end = LocalDateTime.of(1971, Month.MAY, 30, 11, 30);
        DateTimeData data = new DateTimeData(start, end, Duration.ofDays(28));
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-03-30T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-04-27T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-05-25T11:30:00"));
        data.next();
        assertThat(data.getValue(), Matchers.nullValue());
    }

    @Test()
    public void invalidStartAfterEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        LocalDateTime start = LocalDateTime.of(1971, Month.MARCH, 30, 11, 30);
        LocalDateTime end = LocalDateTime.of(1971, Month.MAY, 30, 11, 30);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DateTimeData(end, start, Duration.ofDays(28));
        });
    }

    @Test()
    public void invalidNegativeDuration() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        LocalDateTime start = LocalDateTime.of(1971, Month.MARCH, 30, 11, 30);
        LocalDateTime end = LocalDateTime.of(1971, Month.MAY, 30, 11, 30);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DateTimeData(start, end, Duration.ofDays(-28));
        });
    }

    @Test
    public void testRepeat() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        LocalDateTime start = LocalDateTime.of(1971, Month.MARCH, 30, 11, 30);
        LocalDateTime end = LocalDateTime.of(1971, Month.MAY, 30, 11, 30);
        DateTimeData data = new DateTimeData(start, end, Duration.ofDays(28), LimitBehavior.REPEAT);
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-03-30T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-04-27T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-05-25T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-03-30T11:30:00"));
    }

    @Test
    public void testLoop() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        LocalDateTime start = LocalDateTime.of(1971, Month.MARCH, 30, 11, 30);
        LocalDateTime end = LocalDateTime.of(1971, Month.MAY, 30, 11, 30);
        DateTimeData data = new DateTimeData(start, end, Duration.ofDays(28), LimitBehavior.LOOP);
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-03-30T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-04-27T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-05-25T11:30:00"));
        data.next();
        assertThat(data.getValue().format(DT_FORMAT), Matchers.equalTo("1971-04-22T11:30:00"));
    }
}
