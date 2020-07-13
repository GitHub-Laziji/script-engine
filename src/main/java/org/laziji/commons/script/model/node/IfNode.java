package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public class IfNode extends BaseNode {

    public IfNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {

    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        return null;
    }
}
