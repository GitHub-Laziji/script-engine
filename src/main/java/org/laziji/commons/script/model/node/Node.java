package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;

public interface Node {

    void compile() throws CompileException;

    Value run(Stack<Context> contexts) throws RunException, OperationException;

    String getSegment();
}
