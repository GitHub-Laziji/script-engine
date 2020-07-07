package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;
import java.util.regex.Pattern;

public class VariableNode extends BaseNode {

    private static final Pattern nameReg = Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]*$");
    private String name;

    public VariableNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!nameReg.matcher(this.segment).matches()) {
            throw new CompileException();
        }
        this.name = segment;
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context context = contexts.get(i);
            Value value = context.get(name);
            if (value != null) {
                return value;
            }
        }
        throw new RunException("%s undefined", name);
    }

    public String getName() {
        return name;
    }
}
