package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;

public interface Value {

    Value add(Value o) throws OperationException;

    Value subtract(Value o) throws OperationException;

    Value multiply(Value o) throws OperationException;

    Value divide(Value o) throws OperationException;

    Value copy();
}
