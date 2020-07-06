package org.laziji.commons.script.model.value;

import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.node.Node;
import org.laziji.commons.script.model.node.ReturnNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionValue extends BaseValue {

    private List<Node> nodes;
    private List<String> parameterNames;

    public FunctionValue(List<Node> nodes, List<String> parameterNames) {
        this.nodes = nodes;
        this.parameterNames = parameterNames;
    }

    public Value call(List<Map<String, Value>> contexts, List<Value> parameters) throws RunException, OperationException {
        Map<String, Value> context = new HashMap<>();
        for (int i = 0; i < this.parameterNames.size(); i++) {
            Value value = i < parameters.size() ? parameters.get(i) : UndefinedValue.create();
            context.put(this.parameterNames.get(i), value);
        }
        List<Map<String, Value>> newContexts = new ArrayList<>(contexts);
        newContexts.add(context);
        for (Node node : this.nodes) {
            node.run(newContexts);
            if (node instanceof ReturnNode) {
                return ((ReturnNode) node).getValue();
            }
        }
        return UndefinedValue.create();
    }
}
