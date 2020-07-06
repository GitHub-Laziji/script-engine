package org.laziji.commons.script;

import org.junit.Test;
import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.node.CombinationNode;
import org.laziji.commons.script.model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainTest {

    @Test
    public void test() throws CompileException, RunException, OperationException {
        Map<String, Value> context = new HashMap<>();
        CombinationNode node = new CombinationNode("let a;let b;a = 1+2;b = a*a+3;");
        node.compile();
        node.run(Collections.singletonList(context));
        System.out.println(context.get("b").toString());
    }
}
