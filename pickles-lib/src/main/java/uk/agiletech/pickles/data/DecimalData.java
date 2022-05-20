package uk.agiletech.pickles.data;

import java.math.BigDecimal;

import static uk.agiletech.pickles.data.LimitBehavior.NULL;
import static uk.agiletech.pickles.data.LimitBehavior.RANDOM;

public class DecimalData implements Data<BigDecimal> {

    private final BigDecimal start;
    private final BigDecimal end;
    private final BigDecimal increment;
    private final LimitBehavior limitBehavior;
    private BigDecimal current;

    /**
     * @param start     The value to start with
     * @param end       The value limit at the end
     * @param increment The value by which to increment (or decrement if -ve)
     */
    public DecimalData(BigDecimal start, BigDecimal end, BigDecimal increment) {
        this(start, end, increment, NULL);
    }

    /**
     * @param start         The value to start with
     * @param end           The value limit at the end
     * @param increment     The value by which to increment (or decrement if -ve)
     * @param limitBehavior Indicates the desired behavior when end is reached
     *                      - NULL - value is null
     *                      - LAST_VALUE - the end value is continuously emited
     *                      - LOOP - the sequence wraps arround to the start and repeats the sequence
     */
    DecimalData(BigDecimal start, BigDecimal end, BigDecimal increment, LimitBehavior limitBehavior) {
        this.limitBehavior = limitBehavior;
        if (!((start.compareTo(end) < 0 && increment.compareTo(BigDecimal.ZERO) > 0 || (start.compareTo(end) > 0
                && increment.compareTo(BigDecimal.ZERO) > 0)))) {
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
        BigDecimal previous = current;
        if (limitBehavior == RANDOM) {
            current = randomDecimal();
        } else if (current != null) {
            current = current.add(increment);
            if (positiveIncrement()) {
                if (current.compareTo(end) > 0) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> previous;
                        default -> start.add(current.subtract(start).divideAndRemainder(end.subtract(start))[1]);
                    };
                }
            } else {
                if (current.compareTo(end) < 0) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> previous;
                        default -> start.subtract(start.subtract(current).divideAndRemainder(start.subtract(end))[1]);
                    };
                }
            }
        }
    }

    @Override
    public void reset() {
        this.current = (limitBehavior == RANDOM) ? randomDecimal() : start;
    }

    @Override
    public boolean isGroupable() {
        return limitBehavior == NULL;
    }

    @Override
    public BigDecimal getValue() {
        return current;
    }

    private boolean positiveIncrement() {
        return increment.compareTo(BigDecimal.ZERO) > 0;
    }

    private BigDecimal randomDecimal() {
        return BigDecimal.valueOf(Math.random());
    }
}
