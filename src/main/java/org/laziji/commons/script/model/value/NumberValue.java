package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;

public class NumberValue extends BaseValue {

    private Integer number;

    public NumberValue(String number) {
        this.number = Integer.parseInt(number.trim());
    }

    public NumberValue(Integer number) {
        this.number = number;
    }

    @Override
    public Value add(Value o) throws OperationException {
        if (o instanceof NumberValue) {
            this.number += ((NumberValue) o).getNumber();
            return this;
        }
        throw new OperationException();
    }

    @Override
    public NumberValue copy() {
        return new NumberValue(this.number);
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
