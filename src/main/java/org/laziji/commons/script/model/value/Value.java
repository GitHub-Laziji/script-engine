package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;

public interface Value {

    void setAttribute(String name, Value o);

    Value getAttribute(String name);

    Value add(Value o) throws OperationException;

    Value subtract(Value o) throws OperationException;

    Value multiply(Value o) throws OperationException;

    Value divide(Value o) throws OperationException;

    BooleanValue greater(Value o) throws OperationException;

    BooleanValue greaterOrEqual(Value o) throws OperationException;

    BooleanValue smaller(Value o) throws OperationException;

    BooleanValue smallerOrEqual(Value o) throws OperationException;

    BooleanValue equal(Value o) throws OperationException;

    BooleanValue and(Value o) throws OperationException;

    BooleanValue or(Value o) throws OperationException;

    BooleanValue toBoolean();

    Value copy();
}
