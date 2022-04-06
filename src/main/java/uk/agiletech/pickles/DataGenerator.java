package uk.agiletech.pickles;

public interface DataGenerator<T> {
    boolean end();
    void next();
    T getCurrentValue();
}
