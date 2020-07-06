package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;

public abstract class BaseValue implements Value {

    @Override
    public Value add(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public Value subtract(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public Value multiply(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public Value divide(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public Value copy() {
        return this;
    }
}
