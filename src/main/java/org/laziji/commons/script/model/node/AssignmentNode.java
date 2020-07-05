package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;

import java.util.List;
import java.util.Map;

public class AssignmentNode extends BaseNode {

    private VariableNode variableNode;
    private ExpressionNode expressionNode;

    public AssignmentNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        String[] segments = this.segment.split("=", 2);
        if (segments.length != 2) {
            throw new CompileException();
        }
        this.variableNode = new VariableNode(segments[0]);
        this.variableNode.compile();
        this.expressionNode = new ExpressionNode(segments[1]);
        this.expressionNode.compile();
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException {
        Map<String, Value> context = contexts.get(contexts.size() - 1);
        String name = this.variableNode.getName();
        if (context.get(name) == null) {
            throw new RunException();
        }
        Value value = this.expressionNode.run(contexts);
        context.put(name, value);
        return value;
    }

}
