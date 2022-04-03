package uk.agiletech.pickles;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListValueGenerator<T> implements DataGenerator<T> {

    protected List<T> values;
    private DataGenerator<Integer> indexGenerator;

    ListValueGenerator(@NonNull List<T> values, @NonNull LimitBehavior limitBehavior) {
        this.values = values;
        this.indexGenerator = new IntegerSequence(0, values.size() - 1, 1, limitBehavior);
    }

    @Override
    public boolean hasNext() {
        return indexGenerator.hasNext();
    }

    @Override
    public T next() {
        Integer index = indexGenerator.next();
        T retval;
        if (index == null) {
            retval = null;
        } else {
            retval = index == null ? null : values.get(index);
        }
        return retval;
    }

    class RandomIndex implements DataGenerator<Integer> {
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return ThreadLocalRandom.current().nextInt(0, values.size());
        }
    }
}
