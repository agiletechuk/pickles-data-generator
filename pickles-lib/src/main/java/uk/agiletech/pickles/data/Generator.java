package uk.agiletech.pickles.data;

public interface Generator {
    boolean endSequence();

    void next();

    void reset();

    boolean isGroupable();
}
