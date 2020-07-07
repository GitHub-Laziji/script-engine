package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.FunctionContext;
import org.laziji.commons.script.model.context.LoopContext;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public class BreakNode extends BaseNode {

    public BreakNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!"break".equals(this.segment)) {
            throw new CompileException();
        }
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context context = contexts.get(i);
            context.close();
            if (context instanceof LoopContext) {
                return null;
            }
            if (context instanceof FunctionContext) {
                throw new RunException();
            }
        }
        return null;
    }
}
