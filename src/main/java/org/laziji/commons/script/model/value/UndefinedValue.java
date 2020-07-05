package org.laziji.commons.script.model.value;

public class UndefinedValue extends BaseValue {

    private static final UndefinedValue instance = new UndefinedValue();

    private UndefinedValue() {

    }

    public static UndefinedValue create() {
        return instance;
    }

    @Override
    public String toString() {
        return "undefined";
    }
}
