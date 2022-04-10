package uk.agiletech.pickles.data;

import java.util.concurrent.ThreadLocalRandom;

public class DoubleData implements Data<Double> {

    private final double start;
    private final double end;
    private final double increment;
    private final LimitBehavior limitBehavior;
    private Double current;

    /**
     *
     * @param start     value to start with
     * @param end       value limit to end with
     * @param increment value to increment by or decrement if -ve
     */
    public DoubleData(double start, double end, double increment) {
        this(start, end, increment, LimitBehavior.NULL);
    }

    /**
     *
     * @param start             value to start with
     * @param end               value limit to end with
     * @param increment         value to increment by or decrement if -ve
     * @param limitBehavior     defines the behaviour at when end is reached.
     *                          - NULL: Gives null at the end.
     *                          - LOOP: wraps to the start and goes through sequence again
     *                          - LAST_VALUE: Gives continuously the final value at the end
     */
    DoubleData(double start, double end, double increment, LimitBehavior limitBehavior) {
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
        Double previous = current;
        if (limitBehavior == LimitBehavior.RANDOM) {
            current = randomDouble();
        } else if (current != null) {
            current += increment;
            if (positiveIncrement()) {
                if (current > end) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> previous;
                        default -> start + ((current - start) % (end - start));
                    };
                }
            } else {
                if (current < end) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> previous;
                        default -> start - ((start - current) % (start - end));
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
    public Double getValue() {
        return current;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private double randomDouble() {
        return ThreadLocalRandom.current().nextDouble(start, end);
    }
}
