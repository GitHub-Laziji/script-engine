package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.FunctionContext;
import org.laziji.commons.script.model.node.Node;

import java.util.List;
import java.util.Stack;

public class FunctionValue extends BaseValue {

    private List<Node> nodes;
    private List<String> parameterNames;

    public FunctionValue(List<Node> nodes, List<String> parameterNames) {
        this.nodes = nodes;
        this.parameterNames = parameterNames;
    }

    public Value call(Stack<Context> contexts, List<Value> parameters) throws RunException, OperationException {
        FunctionContext context = new FunctionContext();
        contexts.push(context);
        for (int i = 0; i < this.parameterNames.size(); i++) {
            Value value = i < parameters.size() ? parameters.get(i) : UndefinedValue.create();
            context.put(this.parameterNames.get(i), value);
        }
        for (Node node : this.nodes) {
            if (context.isClose()) {
                break;
            }
            node.run(contexts);
        }
        contexts.pop();
        Value returnValue = context.getReturnValue();
        return returnValue == null ? UndefinedValue.create() : returnValue;
    }
}
