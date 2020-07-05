package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.NumberValue;
import org.laziji.commons.script.model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressionNode extends BaseNode {

    // test
    private List<Node> nodes;

    public ExpressionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        String[] segments = this.segment.split("\\+");
        this.nodes = new ArrayList<>();
        for (String s : segments) {
            Node matchNode = null;
            for (Node node : new Node[]{new ConstantNode(s), new VariableNode(s)}) {
                try {
                    node.compile();
                    matchNode = node;
                } catch (CompileException ignored) {

                }
            }
            if (matchNode == null) {
                throw new CompileException();
            }
            nodes.add(matchNode);
        }
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException {
        NumberValue sum = new NumberValue(0);
        for (Node node : this.nodes) {
            sum.add(node.run(contexts));
        }
        return sum;
    }
}
