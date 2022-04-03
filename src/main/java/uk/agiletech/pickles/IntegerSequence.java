package uk.agiletech.pickles;

public class IntegerSequence implements DataGenerator<Integer> {

    private final int start;
    private final int end;
    private final int increment;
    private Integer current;

    IntegerSequence(int start, int end, int increment) {
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
        return (increment > 0 && current <= end)
                || (increment < 0 && current >= end);
    }


    @Override
    public Integer next() {
        int retval = current;
        current += increment;
        return retval;
    }
}
