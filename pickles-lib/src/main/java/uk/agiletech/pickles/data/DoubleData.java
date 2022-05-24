package uk.agiletech.pickles.data;

import java.util.concurrent.ThreadLocalRandom;

import static uk.agiletech.pickles.data.LimitBehavior.RANDOM;
import static uk.agiletech.pickles.data.LimitBehavior.REPEAT;

public class DoubleData implements Data<Double> {

    private final double start;
    private final double end;
    private final double increment;
    private final LimitBehavior limitBehavior;
    private double current;
    private boolean isnull;

    /**
     * @param start     value to start with
     * @param end       value limit to end with
     * @param increment value to increment by or decrement if -ve
     */
    public DoubleData(double start, double end, double increment) {
        this(start, end, increment, LimitBehavior.NULL);
    }

    /**
     * @param start         value to start with
     * @param end           value limit to end with
     * @param increment     value to increment by or decrement if -ve
     * @param limitBehavior defines the behaviour at when end is reached.
     *                      - NULL: Gives null at the end.
     *                      - LOOP: wraps to the start and goes through sequence again
     *                      - LAST_VALUE: Gives continuously the final value at the end
     */
    DoubleData(double start, double end, double increment, LimitBehavior limitBehavior) {
        this.limitBehavior = limitBehavior;
        if (!((start < end && increment > 0) || (start > end && increment < 0))) {
            throw new IllegalArgumentException("start must be before end for the given increment");
        }
        this.start = start;
        this.end = end;
        this.increment = increment;
        reset();
    }

    @Override
    public boolean endSequence() {
        return isnull;
    }

    @Override
    public void next() {
        if (limitBehavior == RANDOM) {
            current = randomDouble();
        } else if (!isnull) {
            double next = current + increment;
            if (positiveIncrement()) {
                if (next > end || next < start) {
                    if (limitBehavior == LimitBehavior.NULL) {
                        isnull = true;
                    } else if (limitBehavior == LimitBehavior.LOOP) {
                        current = start + ((current - start) % (end - start));
                    } else if (limitBehavior == REPEAT) {
                        current = start;
                    }
                } else {
                    current = next;
                }
            } else {
                if (next < end || next > start) {
                    if (limitBehavior == LimitBehavior.NULL) {
                        isnull = true;
                    } else if (limitBehavior == LimitBehavior.LOOP) {
                        current = start - ((start - current) % (start - end));
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
        this.current = (limitBehavior == RANDOM) ? randomDouble() : start;
        this.isnull = false;
    }

    @Override
    public boolean isGroupable() {
        return limitBehavior == LimitBehavior.NULL;
    }

    public double getDoubleValue() {
        return current;
    }

    @Override
    public Double getValue() {
        return isnull ? null : current;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private double randomDouble() {
        return ThreadLocalRandom.current().nextDouble(start, end);
    }
}
