package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class VariableNode extends BaseNode {

    private static final Pattern nameReg = Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*$");
    private String name;

    public VariableNode (String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if(!nameReg.matcher(this.segment).matches()){
            throw new CompileException();
        }
    }

    @Override
    public Object run(List<Map<String, Value>> contexts) throws RunException {
        return null;
    }
}
