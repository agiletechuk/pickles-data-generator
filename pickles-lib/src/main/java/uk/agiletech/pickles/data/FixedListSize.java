package uk.agiletech.pickles.data;

public class FixedListSize implements ListSize {
    private final int size;

    public FixedListSize(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

}
