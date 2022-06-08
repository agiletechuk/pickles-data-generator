package uk.agiletech.pickles.data;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static uk.agiletech.pickles.data.LimitBehavior.LOOP;
import static uk.agiletech.pickles.data.LimitBehavior.RANDOM;

public class ListValueData<T> implements Data<T> {

    protected List<T> values;
    private final Data<Integer> indexGenerator;
    private final LimitBehavior limitBehavior;

    public ListValueData(@NonNull List<T> values, @NonNull LimitBehavior limitBehavior) {
        this.limitBehavior = limitBehavior;
        this.indexGenerator = new IntegerData(0, values.size() - 1, 1, limitBehavior == RANDOM ? LOOP : limitBehavior);
        if (limitBehavior == RANDOM) {
            this.values = new ArrayList<>(values);
            reset();
        } else {
            this.values = values;
        }
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
        if (limitBehavior == RANDOM) {
            Collections.shuffle(this.values);
        }
    }

    @Override
    public boolean isGroupable() {
        return indexGenerator.isGroupable();
    }

    @Override
    public T getValue() {
        return indexGenerator.getValue() == null ? null : values.get(indexGenerator.getValue());
    }
}
