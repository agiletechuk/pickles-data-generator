package uk.agiletech.pickles;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerGenerator implements DataGenerator<Integer> {

    private final int start;
    private final int end;
    private final int increment;
    private final LimitBehavior limitBehavior;
    private Integer current;

    /**
     *
     * @param start
     * @param end
     * @param increment
     */
    IntegerGenerator(int start, int end, int increment) {
        this(start, end, increment, LimitBehavior.NULL);
    }

    /**
     *
     * @param start
     * @param end
     * @param increment
     * @param limitBehavior
     */
    IntegerGenerator(int start, int end, int increment, LimitBehavior limitBehavior) {
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
    public boolean end() {
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
    public Integer getCurrentValue() {
        return current;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private int randomInt() {
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
