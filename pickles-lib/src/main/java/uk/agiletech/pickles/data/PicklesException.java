package uk.agiletech.pickles.data;

public class PicklesException extends RuntimeException {
    public PicklesException(String message) {
        super(message);
    }

    public PicklesException(String message, Exception e) {
        super(message, e);
    }
}
