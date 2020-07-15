package org.laziji.commons.script;

import org.junit.Test;
import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.BlockContext;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.node.CombinationNode;

import java.util.Stack;

public class MainTest {

    @Test
    public void test() throws CompileException, RunException, OperationException {
        Stack<Context> contexts = new Stack<>();
        contexts.push(new BlockContext());
        CombinationNode node = new CombinationNode(
                "function sum(n) {\n" +
                        "  if(n == 1) {\n" +
                        "    return 1;\n" +
                        "  }\n" +
                        "  return n + sum(n - 1);\n" +
                        "}\n" +
                        "" +
                        "let a = 3;\n" +
                        "let b = 4;\n" +
                        "let c = a + 3 + sum(b);\n" +
                        "" +
                        "while(a > 0) {\n" +
                        "  c = c + a;\n" +
                        "  a = a - 1;\n" +
                        "}");
        node.compile();
        node.run(contexts);
        System.out.println(contexts.get(0).get("c").toString());
    }
}
