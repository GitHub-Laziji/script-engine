package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public class SingleNode extends BaseNode {

    private Node node;

    public SingleNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        for (Node node : new Node[]{new ConstantNode(this.segment), new VariableNode(this.segment)}) {
            try {
                node.compile();
                this.node = node;
            } catch (CompileException ignored) {
            }
        }
        if (this.node == null) {
            throw new CompileException();
        }
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        return node.run(contexts);
    }

}
