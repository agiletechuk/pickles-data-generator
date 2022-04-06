package uk.agiletech.pickles;

import org.springframework.lang.NonNull;

import java.util.List;

public class ListValueGenerator<T> implements DataGenerator<T> {

    protected List<T> values;
    private DataGenerator<Integer> indexGenerator;

    ListValueGenerator(@NonNull List<T> values, @NonNull LimitBehavior limitBehavior) {
        this.values = values;
        this.indexGenerator = new IntegerGenerator(0, values.size() - 1, 1, limitBehavior);
    }

    @Override
    public boolean end() {
        return indexGenerator.end();
    }

    @Override
    public void next() {
        indexGenerator.next();
    }

    @Override
    public T getCurrentValue() {
        return indexGenerator.getCurrentValue() == null ? null : values.get(indexGenerator.getCurrentValue());
    }
}
