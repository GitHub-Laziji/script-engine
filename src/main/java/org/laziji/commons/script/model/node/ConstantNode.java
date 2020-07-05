package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.NumberValue;
import org.laziji.commons.script.model.value.Value;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ConstantNode extends BaseNode {

    private Value value;

    public ConstantNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!Pattern.compile("^\\d+$").matcher(this.segment).matches()) {
            throw new CompileException();
        }
        this.value = new NumberValue(this.segment);
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException {
        return value;
    }
}
