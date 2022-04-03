package uk.agiletech.pickles;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerSequence implements DataGenerator<Integer> {

    private final int start;
    private final int end;
    private final int increment;
    private Integer current;
    private LimitBehavior limitBehavior;

    IntegerSequence(int start, int end, int increment) {
        this(start, end, increment, LimitBehavior.NULL);
    }

    IntegerSequence(int start, int end, int increment, LimitBehavior limitBehavior) {
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
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Integer next() {
        Integer retval = current;
        if (limitBehavior == LimitBehavior.RANDOM) {
            current = randomInt();
        } else if (current != null) {
            current += increment;
            if (positiveIncrement()) {
                if (current > end) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> retval;
                        default -> start + ((current - start) % (end - start + 1));
                    };
                }
            } else {
                if (current < end) {
                    current = switch (limitBehavior) {
                        case NULL -> null;
                        case LAST_VALUE -> retval;
                        default -> start - ((start - current) % (start - end + 1));
                    };
                }
            }
        }
        return retval;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }

    private int randomInt() {
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
