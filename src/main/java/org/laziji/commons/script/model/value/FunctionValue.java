package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.FunctionContext;
import org.laziji.commons.script.model.node.CombinationNode;

import java.util.List;
import java.util.Stack;

public class FunctionValue extends BaseValue {

    private CombinationNode node;
    private List<String> parameterNames;

    public FunctionValue(CombinationNode node, List<String> parameterNames) {
        this.node = node;
        this.parameterNames = parameterNames;
    }

    public Value call(Stack<Context> contexts, List<Value> parameters) throws RunException, OperationException {
        FunctionContext context = new FunctionContext();
        contexts.push(context);
        for (int i = 0; i < this.parameterNames.size(); i++) {
            Value value = i < parameters.size() ? parameters.get(i) : UndefinedValue.create();
            context.put(this.parameterNames.get(i), value);
        }
        node.run(contexts);
        contexts.pop();
        Value returnValue = context.getReturnValue();
        return returnValue == null ? UndefinedValue.create() : returnValue;
    }
}
