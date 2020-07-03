package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;
import org.laziji.commons.script.model.value.VariableValue;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class VariableDefinitionNode extends BaseNode {

    private VariableValue variableValue;

    public VariableDefinitionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!segment.startsWith("let ")) {
            throw new CompileException("Expect 'let' got '%s'", segment.substring(0, Math.min(segment.length(), 4)));
        }
        if(!Pattern.compile("").matcher(segment.substring(3)).matches()){
            throw new CompileException();
        }
    }

    @Override
    public Object run(List<Map<String, Value>> contexts) throws RunException {
        return null;
    }
}
