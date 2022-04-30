package uk.agiletech.pickles.data;

import java.util.concurrent.ThreadLocalRandom;

import static uk.agiletech.pickles.data.LimitBehavior.LAST_VALUE;
import static uk.agiletech.pickles.data.LimitBehavior.NULL;

/**
 * Generate range of integers. The start, end and increment can be configured
 * The sequence can go up or down or be skip around randomly
 * At the end of the sequence, can be null, the last value or loop to the start
 */
public class IntegerData implements Data<Integer> {

    private final int start;
    private final int end;
    private final int increment;
    private final LimitBehavior limitBehavior;
    private int current;
    private boolean isnull;

    /**
     * Create an IntegerGenerator with default limit behavior = LimitBehavior.NULL
     *
     * @param start     start value
     * @param end       end value
     * @param increment increment add (or subtracted if negative) to the current value
     */
    public IntegerData(int start, int end, int increment) {
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
    public IntegerData(int start, int end, int increment, LimitBehavior limitBehavior) {
        this.limitBehavior = limitBehavior;
        if (!((start < end && increment > 0) || (start > end && increment < 0))) {
            throw new IllegalArgumentException("start must be before end for the given increment");
        }
        this.start = start;
        this.end = end;
        this.increment = increment;
        this.current = start;
        isnull = false;
    }

    @Override
    public boolean endSequence() {
        return isnull;
    }

    @Override
    public void next() {
        if (limitBehavior == LimitBehavior.RANDOM) {
            current = randomInt();
        } else if (!isnull) {
            int next = current + increment;
            if (positiveIncrement()) {
                if (next > end) {
                    if (limitBehavior == NULL) {
                        isnull = true;
                    } else if (limitBehavior == LAST_VALUE) {
                        // do nothing
                    } else {
                        current = start + ((next - start) % (end - start + 1));
                    }
                } else {
                    current = next;
                }
            } else {
                if (next < end) {
                    if (limitBehavior == NULL) {
                        isnull = true;
                    } else if (limitBehavior == LAST_VALUE) {
                        // do nothing
                    } else {
                        current = start - ((start - next) % (start - end + 1));
                    }
                } else {
                    current = next;
                }
            }
        }
    }

    @Override
    public void reset() {
        current = start;
    }

    @Override
    public boolean isGroupable() {
        return limitBehavior == NULL;
    }

    public int getIntValue() {
        return current;
    }

    @Override
    public Integer getValue() {
        return isnull ? null : current;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private int randomInt() {
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
