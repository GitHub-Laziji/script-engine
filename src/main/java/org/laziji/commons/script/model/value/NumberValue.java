package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;

public class NumberValue extends BaseValue {

    private Integer value;

    public NumberValue(String value) {
        this.value = Integer.parseInt(value.trim());
    }

    public NumberValue(Integer value) {
        this.value = value;
    }

    @Override
    public Value add(Value o) throws OperationException {
        if (o instanceof NumberValue) {
            this.value += ((NumberValue) o).getValue();
            return this;
        }
        throw new OperationException();
    }

    @Override
    public Value subtract(Value o) throws OperationException {
        if (o instanceof NumberValue) {
            this.value -= ((NumberValue) o).getValue();
            return this;
        }
        throw new OperationException();
    }

    @Override
    public Value multiply(Value o) throws OperationException {
        if (o instanceof NumberValue) {
            this.value *= ((NumberValue) o).getValue();
            return this;
        }
        throw new OperationException();
    }

    @Override
    public Value divide(Value o) throws OperationException {
        if (o instanceof NumberValue) {
            this.value /= ((NumberValue) o).getValue();
            return this;
        }
        throw new OperationException();
    }

    @Override
    public NumberValue copy() {
        return new NumberValue(this.value);
    }

    @Override
    public BooleanValue toBoolean() {
        return value == 0 ? BooleanValue.getFalseInstance() : BooleanValue.getTrueInstance();
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
