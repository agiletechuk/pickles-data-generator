package uk.agiletech.pickles;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static uk.agiletech.pickles.Behavior.*;

public class ListValueGenerator<T> implements DataGenerator<T> {

    protected int index;
    protected List<T> values;
    private Behavior behavior;

    ListValueGenerator(@NonNull List<T> values, @NonNull Behavior behavior) {
        this.behavior = behavior;
        this.values = values;
        this.index = this.behavior == RANDOM ? getRandomIndex() : 0;
    }

    @Override
    public boolean hasNext() {
        return index < values.size();
    }

    @Override
    public T next() {
        T retval = values.get(index);
        nextIndex();
        if (behavior == LOOP && !hasNext()) {
            index = 0;
        }
        return retval;
    }

    private int nextIndex() {
        return behavior == RANDOM ? getRandomIndex() : index++;
    }


    private int getRandomIndex() {
        return ThreadLocalRandom.current().nextInt(0,values.size());
    }

}
