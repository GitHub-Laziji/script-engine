package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;

import java.util.Arrays;
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
        String[] segments = this.segment.split("\\s+", 3);
        if (segments.length != 3 || !"=".equals(segments[1])) {
            throw new CompileException();
        }
        this.variableNode = new VariableNode(segments[0]);
        this.variableNode.compile();
        this.expressionNode = new ExpressionNode(segments[2]);
        this.expressionNode.compile();
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException {
        return null;
    }

}
