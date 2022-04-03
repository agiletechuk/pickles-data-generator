package uk.agiletech.pickles;

public interface DataGenerator<T> {
    boolean hasNext();
    void next();
    T getCurrentValue();
}
