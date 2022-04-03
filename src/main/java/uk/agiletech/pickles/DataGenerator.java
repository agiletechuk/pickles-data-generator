package uk.agiletech.pickles;

public interface DataGenerator<T> {
    boolean hasNext();
    T next();
}
