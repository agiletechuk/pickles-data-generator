package uk.agiletech.pickles;

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
        if (current != null) {
            current += increment;
            if (positiveIncrement()) {
                if (current > end) {
                    current = switch (limitBehavior) {
                        case LOOP -> current % (end - start + 1);
                        case NULL -> null;
                        case LAST_VALUE -> retval;
                    };
                }
            } else {
                if (current < end) {
                    current = switch (limitBehavior) {
                        case LOOP -> current % (start - end + 1);
                        case NULL -> null;
                        case LAST_VALUE -> retval;
                    };
                }
            }
        }
        return retval;
    }

    private boolean positiveIncrement() {
        return increment > 0;
    }
}
