package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;

import java.util.List;
import java.util.Map;

public class FunctionDefinitionNode extends BaseNode{

    public FunctionDefinitionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {

    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException {
        return null;
    }
}
