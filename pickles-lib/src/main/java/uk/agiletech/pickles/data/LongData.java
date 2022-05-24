package uk.agiletech.pickles.data;

import java.util.concurrent.ThreadLocalRandom;

import static uk.agiletech.pickles.data.LimitBehavior.*;

/**
 * Generate range of longs. The start, end and increment can be configured
 * The sequence can go up or down or skip around randomly
 * At the end of the sequence, can be last value, null or loop to the start
 */
public class LongData implements Data<Long> {

    private final long start;
    private final long end;
    private final long increment;
    private final LimitBehavior limitBehavior;
    private final long randomEnd;
    private long current;
    private boolean isnull;

    /**
     * Create a LongGenerator with default limit behavior = LimitBehavior.NULL
     *
     * @param start     start value
     * @param end       end value
     * @param increment increment add (or subtracted if negative) to the current value
     */
    public LongData(long start, long end, long increment) {
        this(start, end, increment, NULL);
    }

    /**
     * Create a LongGenerator
     *
     * @param start         start value
     * @param end           end value
     * @param increment     increment
     * @param limitBehavior the value at the end of the sequence
     */
    public LongData(long start, long end, long increment, LimitBehavior limitBehavior) {
        this.limitBehavior = limitBehavior;
        if (!((start < end && increment > 0) || (start > end && increment < 0))) {
            throw new IllegalArgumentException("start must be before end for the given increment");
        }
        this.start = start;
        this.end = end;
        this.randomEnd = end == Long.MAX_VALUE ? end : end + 1;
        this.increment = increment;
        reset();
    }

    @Override
    public boolean endSequence() {
        return isnull;
    }

    @Override
    public void next() {
        if (limitBehavior == LimitBehavior.RANDOM) {
            current = randomLong();
        } else if (!isnull) {
            long next = current + increment;
            if (positiveIncrement()) {
                if (next > end) {
                    if (limitBehavior == NULL) {
                        isnull = true;
                    } else if (limitBehavior == LOOP) {
                        current = start + ((next - start) % (end - start + 1));
                    } else if (limitBehavior == REPEAT) {
                        current = start;
                    }
                } else {
                    current = next;
                }
            } else {
                if (next < end) {
                    if (limitBehavior == NULL) {
                        isnull = true;
                    } else if (limitBehavior == LOOP) {
                        current = start - ((start - next) % (start - end + 1));
                    } else if (limitBehavior == REPEAT) {
                        current = start;
                    }
                } else {
                    current = next;
                }
            }
        }
    }

    @Override
    public void reset() {
        this.current = limitBehavior == RANDOM ? randomLong() : start;
        this.isnull = false;
    }

    @Override
    public boolean isGroupable() {
        return limitBehavior == NULL;
    }

    public long getLongValue() {
        return current;
    }

    @Override
    public Long getValue() {
        return isnull ? null : current;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private long randomLong() {
        return ThreadLocalRandom.current().nextLong(start, randomEnd);
    }
}
