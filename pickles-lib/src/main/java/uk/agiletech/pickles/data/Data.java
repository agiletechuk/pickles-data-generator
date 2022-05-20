package uk.agiletech.pickles.data;

import uk.agiletech.pickles.format.Format;

public interface Data<T> extends Generator, Format<T> {
    boolean endSequence();
    void next();
    T getValue();
}
