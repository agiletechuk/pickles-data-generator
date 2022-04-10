package uk.agiletech.pickles.data;

import org.springframework.lang.NonNull;

import java.util.List;

public class ListValueData<T> implements Data<T> {

    protected List<T> values;
    private final Data<Integer> indexGenerator;

    public ListValueData(@NonNull List<T> values, @NonNull LimitBehavior limitBehavior) {
        this.values = values;
        this.indexGenerator = new IntegerData(0, values.size() - 1, 1, limitBehavior);
    }

    @Override
    public boolean endSequence() {
        return indexGenerator.endSequence();
    }

    @Override
    public void next() {
        indexGenerator.next();
    }

    @Override
    public void reset() {
        indexGenerator.reset();
    }

    @Override
    public T getValue() {
        return indexGenerator.getValue() == null ? null : values.get(indexGenerator.getValue());
    }
}
