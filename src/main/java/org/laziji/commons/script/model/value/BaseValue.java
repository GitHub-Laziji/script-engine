package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;

public abstract class BaseValue implements Value {

    @Override
    public void setAttribute(String name, Value o) {

    }

    @Override
    public Value getAttribute(String name) {
        return null;
    }

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
    public BooleanValue greater(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public BooleanValue greaterOrEqual(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public BooleanValue smaller(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public BooleanValue smallerOrEqual(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public BooleanValue equal(Value o) throws OperationException {
        throw new OperationException("Unrealized.");
    }

    @Override
    public BooleanValue and(Value o) {
        if (this.toBoolean().getValue() && o.toBoolean().getValue()) {
            return BooleanValue.getTrueInstance();
        }
        return BooleanValue.getFalseInstance();
    }

    @Override
    public BooleanValue or(Value o) {
        if (this.toBoolean().getValue() || o.toBoolean().getValue()) {
            return BooleanValue.getTrueInstance();
        }
        return BooleanValue.getFalseInstance();
    }

    @Override
    public BooleanValue toBoolean() {
        return BooleanValue.getTrueInstance();
    }

    @Override
    public Value copy() {
        return this;
    }
}
