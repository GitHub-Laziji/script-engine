package org.laziji.commons.script.model.context;

import org.laziji.commons.script.model.value.Value;

public class FunctionContext extends BaseContext {

    private Value returnValue;

    public void setReturnValue(Value returnValue) {
        this.returnValue = returnValue;
    }

    public Value getReturnValue() {
        return returnValue;
    }
}
