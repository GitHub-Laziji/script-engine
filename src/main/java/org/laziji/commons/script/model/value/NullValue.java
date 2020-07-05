package org.laziji.commons.script.model.value;

public class NullValue extends BaseValue {

    private static final NullValue instance = new NullValue();

    private NullValue() {

    }

    public static NullValue create() {
        return instance;
    }
}
