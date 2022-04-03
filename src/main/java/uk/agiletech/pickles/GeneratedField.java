package uk.agiletech.pickles;

public class GeneratedField<T> {
    String field;
    DataGenerator<T> generatpr;

    public GeneratedField(String field, DataGenerator<T> generatpr) {
        this.field = field;
        this.generatpr = generatpr;
    }
}
