package uk.agiletech.pickles.data;

public class PickleException extends RuntimeException {
    public PickleException(String message) {
        super(message);
    }

    public PickleException(String message, Exception e) {
        super(message, e);
    }
}
