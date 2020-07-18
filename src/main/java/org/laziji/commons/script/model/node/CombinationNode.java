package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;
import org.laziji.commons.script.util.TextUtils;

import java.util.*;

public class CombinationNode extends BaseNode {

    private List<Node> nodes;

    public CombinationNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        this.nodes = new ArrayList<>();
        for (String s : TextUtils.splitSegment(this.segment)) {
            if (s.isEmpty()) {
                continue;
            }
            Node matchNode = null;
            for (Node node : new Node[]{
                    new FunctionDefinitionNode(s),
                    new VariableDefinitionNode(s),
                    new AssignmentNode(s),
                    new IfNode(s),
                    new WhileNode(s),
                    new ReturnNode(s),
                    new PrintNode(s)
            }) {
                try {
                    node.compile();
                    matchNode = node;
                    break;
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
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        for (Node node : this.nodes) {
            if (contexts.peek().isClose()) {
                return null;
            }
            node.run(contexts);
        }
        return null;
    }


}
