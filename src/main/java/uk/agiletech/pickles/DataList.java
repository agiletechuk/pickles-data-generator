package uk.agiletech.pickles;

public interface DataList extends Data {
    int getSize();
    Data getData(int index);
}
