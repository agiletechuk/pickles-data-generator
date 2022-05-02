package uk.agiletech.pickles.data;

public class ListData implements Data<String> {

    private final String separator;
    private final String prefix;
    private final String suffix;
    private final Data<?> dataValues;
    private final ListSize listSize;
    private int maxSize;

    public ListData(Data<?> dataValues, ListSize listSize) {
        this(",", "[", "]", dataValues, listSize);
    }

    public ListData(String separator, String prefix, String suffix, Data<?> dataValues, ListSize listSize) {
        this.separator = separator;
        this.prefix = prefix;
        this.suffix = suffix;
        this.dataValues = dataValues;
        this.listSize = listSize;
        this.maxSize = listSize.getSize();
    }

    @Override
    public boolean endSequence() {
        return false;
    }

    @Override
    public void next() {
        this.maxSize = listSize.getSize();
    }

    @Override
    public void reset() {
        // nothing to do
    }

    @Override
    public boolean isGroupable() {
        return false;
    }

    @Override
    public String getValue() {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        int count = 0;
        dataValues.reset();
        while (!dataValues.endSequence() && count++ < maxSize) {
            if (count > 1) builder.append(separator);
            builder.append(dataValues.getValue());
            dataValues.next();
        }
        builder.append(suffix);
        return builder.toString();
    }
}
