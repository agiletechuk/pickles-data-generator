package uk.agiletech.pickles.data;

import java.util.concurrent.ThreadLocalRandom;

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
    private Integer current;

    /**
     * Create an IntegerGenerator with default limit behavior = LimitBehavior.NULL
     *
     * @param start     start value
     * @param end       end value
     * @param increment increment add (or subtracted if negative) to the current value
     */
    public IntegerData(int start, int end, int increment) {
        this(start, end, increment, LimitBehavior.NULL);
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
    }

    @Override
    public boolean endSequence() {
        return current == null;
    }

    @Override
    public void next() {
        Integer previous = current;
        if (limitBehavior == LimitBehavior.RANDOM) {
            current = randomInt();
        } else if (current != null) {
            current += increment;
            if (positiveIncrement()) {
                if (current > end) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> previous;
                        default -> start + ((current - start) % (end - start + 1));
                    };
                }
            } else {
                if (current < end) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> previous;
                        default -> start - ((start - current) % (start - end + 1));
                    };
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
        return limitBehavior == LimitBehavior.NULL;
    }

    @Override
    public Integer getValue() {
        return current;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private int randomInt() {
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
