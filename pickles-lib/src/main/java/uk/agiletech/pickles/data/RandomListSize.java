package uk.agiletech.pickles.data;

import uk.agiletech.pickles.PicklesException;

import java.util.concurrent.ThreadLocalRandom;

public class RandomListSize implements ListSize {

    private final int min;
    private final int max;

    public RandomListSize(int min, int max) {
        if (min < 0) {
            throw new PicklesException("min can not be negative");
        }
        if (min > max) {
            throw new PicklesException("min must be less than max");
        }
        this.min = min;
        this.max = max == Integer.MAX_VALUE ? max : max + 1;
    }

    @Override
    public int getSize() {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
