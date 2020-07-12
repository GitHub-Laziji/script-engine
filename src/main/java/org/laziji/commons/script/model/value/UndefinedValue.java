package org.laziji.commons.script.model.value;

public class UndefinedValue extends BaseValue {

    private static final UndefinedValue instance = new UndefinedValue();

    private UndefinedValue() {

    }

    public static UndefinedValue getInstance() {
        return instance;
    }

    @Override
    public BooleanValue toBoolean() {
        return BooleanValue.getFalseInstance();
    }

    @Override
    public String toString() {
        return "undefined";
    }
}
