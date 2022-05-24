package uk.agiletech.pickles.data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.ThreadLocalRandom;

import static uk.agiletech.pickles.data.LimitBehavior.*;

/**
 * Generate range of integers. The start, end and increment can be configured
 * The sequence can go up or down or be skip around randomly
 * At the end of the sequence, can be null, the last value or loop to the start
 */
public class DateTimeData implements Data<LocalDateTime> {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Duration increment;
    private final LimitBehavior limitBehavior;
    private LocalDateTime current;

    /**
     * Create an IntegerGenerator with default limit behavior = LimitBehavior.NULL
     *
     * @param start     start value
     * @param end       end value
     * @param increment increment add (or subtracted if negative) to the current value
     */
    public DateTimeData(LocalDateTime start, LocalDateTime end, Duration increment) {
        this(start, end, increment, NULL);
    }

    /**
     * Create an IntegerGenerator
     *
     * @param start         start value
     * @param end           end value
     * @param increment     increment
     * @param limitBehavior the value at the end of the sequence
     */
    public DateTimeData(LocalDateTime start, LocalDateTime end, Duration increment, LimitBehavior limitBehavior) {
        this.limitBehavior = limitBehavior;
        if (!((start.isBefore(end) && !increment.isNegative())
                || (start.isAfter(end) && increment.isNegative()))) {
            throw new IllegalArgumentException("start must be before end for the given increment");
        }
        this.start = start;
        this.end = end;
        this.increment = increment;
        reset();
    }

    @Override
    public boolean endSequence() {
        return current == null;
    }

    @Override
    public void next() {
        if (limitBehavior == LimitBehavior.RANDOM) {
            current = randomDateTime();
        } else if (current != null) {
            LocalDateTime next = current.plus(increment);
            if (!increment.isNegative()) {
                if (next.isAfter(end)) {
                    if (limitBehavior == NULL) {
                        current = null;
                    } else if (limitBehavior == LOOP) {
                        current = start.plus(Duration.between(end,next));
                    } else if (limitBehavior == REPEAT) {
                        current = start;
                    }
                } else {
                    current = next;
                }
            } else {
                if (next.isBefore(end)) {
                    if (limitBehavior == NULL) {
                        current = null;
                    } else if (limitBehavior == LOOP) {
                        current = start.plus(Duration.between(next,end));
                    } else if (limitBehavior == REPEAT) {
                        current = start;
                    }
                } else {
                    current = next;
                }
            }
        }
    }

    private LocalDateTime randomDateTime() {
        return null;
    }

    @Override
    public void reset() {
        this.current = (limitBehavior == RANDOM) ? randomDateTime() : start;
    }

    @Override
    public boolean isGroupable() {
        return limitBehavior == NULL;
    }

    @Override
    public LocalDateTime getValue() {
        return current;
    }
}
