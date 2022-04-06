package uk.agiletech.pickles;

import java.io.IOException;
import java.util.List;

public class StringFieldFormat implements FieldFormat {
    private final String format;
    private final List<? extends DataGenerator> dataGenerators;

    public StringFieldFormat(String format, List<? extends DataGenerator> dataGenerators) {
        this.format = format;
        this.dataGenerators = dataGenerators;
    }

    @Override
    public String getValue() {
        List<?> valueList = dataGenerators.stream().map(DataGenerator::getCurrentValue).toList();
        return String.format(format, valueList.toArray());
    }
}
