package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.FunctionContext;
import org.laziji.commons.script.model.context.LoopContext;
import org.laziji.commons.script.model.context.LoopUnitContext;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public class ContinueNode extends BaseNode {

    public ContinueNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!"continue".equals(this.segment)) {
            throw new CompileException();
        }
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context context = contexts.get(i);
            context.close();
            if (context instanceof LoopUnitContext) {
                return null;
            }
            if (context instanceof FunctionContext) {
                throw new RunException();
            }
        }
        throw new RunException();
    }
}
