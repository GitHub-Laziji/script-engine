package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.FunctionContext;
import org.laziji.commons.script.model.value.UndefinedValue;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public class ReturnNode extends BaseNode {

    private ExpressionNode expressionNode;

    public ReturnNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (this.segment.equals("return")) {

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
        Value value = UndefinedValue.create();
        if (this.expressionNode != null) {
            value = this.expressionNode.run(contexts);
        }
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context context = contexts.get(i);
            context.close();
            if (context instanceof FunctionContext) {
                ((FunctionContext) context).setReturnValue(value);
                return null;
            }
        }
        throw new RunException();
    }
}
