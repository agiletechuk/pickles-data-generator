package uk.agiletech.pickles.data;

import java.util.List;

public class CombinedData implements Data<Object> {
    private final LimitBehavior limitBehavior;
    ListValueData<Data<?>> listValueData;
    List<Data<?>> data;

    public CombinedData(List<Data<?>> data, LimitBehavior limitBehavior) {
        listValueData = new ListValueData<>(data, limitBehavior);
        this.data = data;
        this.limitBehavior = limitBehavior;
    }

    @Override
    public boolean endSequence() {
        return listValueData.endSequence() || listValueData.getValue().endSequence();
    }

    @Override
    public void next() {
        Data<?> data = listValueData.getValue();
        if (data != null) {
            data.next();
            listValueData.next();
        }
        if (listValueData.endSequence()) {
            listValueData.reset();
        }
        if ((limitBehavior == LimitBehavior.LOOP || limitBehavior == LimitBehavior.RANDOM )
                && listValueData.getValue().endSequence()) {
            reset();
        }
    }

    @Override
    public void reset() {
        listValueData.reset();
        data.forEach(Data::reset);
    }

    @Override
    public boolean isGroupable() {
        return false;
    }

    @Override
    public Object getValue() {
        return listValueData.getValue() == null ? null : listValueData.getValue().getValue();
    }
}
