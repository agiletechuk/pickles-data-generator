package uk.agiletech.pickles.format;

import java.util.List;

public class StringFormat implements Format<String> {
    private final String format;
    private final List<? extends Format<?>> dataValues;

    public StringFormat(String format, List<? extends Format<?>> dataValues) {
        this.format = format;
        this.dataValues = dataValues;
    }

    @Override
    public String getValue() {
        List<?> valueList = dataValues.stream().map(Format::getValue).toList();
        return String.format(format, valueList.toArray());
    }
}
