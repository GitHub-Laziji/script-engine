package org.laziji.commons.script;

import org.junit.Test;
import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.BlockContext;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.node.CombinationNode;
import org.laziji.commons.script.model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MainTest {

    @Test
    public void test() throws CompileException, RunException, OperationException {
        Stack<Context> contexts = new Stack<>();
        contexts.push(new BlockContext());
        CombinationNode node = new CombinationNode("let a;let b;a = 1+2;b = a*a+3;");
        node.compile();
        node.run(contexts);
        System.out.println(contexts.get(0).get("b").toString());
    }
}
