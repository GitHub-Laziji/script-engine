package org.laziji.commons.script.model.value;

public class NumberValue extends BaseValue {

    private Integer number;

    public NumberValue(String number) {
        this.number = Integer.parseInt(number.trim());
    }

    public NumberValue(Integer number) {
        this.number = number;
    }

    public NumberValue add(Value o) {
        if (o instanceof NumberValue) {
            this.number += ((NumberValue) o).getNumber();
            return this;
        }
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
