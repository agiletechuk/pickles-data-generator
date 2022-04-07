package uk.agiletech.pickles.data;

import java.util.UUID;

public class UUIDData implements Data<UUID> {
    private UUID current;

    public UUIDData() {
        next();
    }

    @Override
    public boolean endSequence() {
        return false;
    }

    @Override
    public void next() {
        current = UUID.randomUUID();
    }

    @Override
    public UUID getValue() {
        return current;
    }
}
