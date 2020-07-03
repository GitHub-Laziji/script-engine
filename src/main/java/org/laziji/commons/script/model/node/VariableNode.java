package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;
import org.laziji.commons.script.model.value.VariableValue;

import java.util.List;
import java.util.Map;

public class VariableNode extends BaseNode {

    private VariableValue variableValue;

    public VariableNode (String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {

    }

    @Override
    public Object run(List<Map<String, Value>> contexts) throws RunException {
        return null;
    }
}
