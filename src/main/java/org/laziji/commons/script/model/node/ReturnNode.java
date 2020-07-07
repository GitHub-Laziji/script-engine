package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.UndefinedValue;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public class ReturnNode extends BaseNode {

    private ExpressionNode expressionNode;
    private Value value;

    public ReturnNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (this.segment.equals("return")) {
            this.value = UndefinedValue.create();
            return;
        }
        if (!this.segment.startsWith("return ")) {
            throw new CompileException();
        }
        this.expressionNode = new ExpressionNode(this.segment.substring(7));
        this.expressionNode.compile();
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        if (this.expressionNode != null) {
            this.value = this.expressionNode.run(contexts);
        }
        return null;
    }

    public Value getValue() {
        return value;
    }
}
