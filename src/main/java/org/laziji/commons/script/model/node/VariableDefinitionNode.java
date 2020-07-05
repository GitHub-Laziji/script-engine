package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.UndefinedValue;
import org.laziji.commons.script.model.value.Value;

import java.util.List;
import java.util.Map;

public class VariableDefinitionNode extends BaseNode {

    private VariableNode variableNode;

    public VariableDefinitionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!segment.startsWith("let ")) {
            throw new CompileException("Expect 'let' got '%s'", segment.substring(0, Math.min(segment.length(), 4)));
        }
        this.variableNode = new VariableNode(segment.substring(3));
        this.variableNode.compile();
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException {
        Map<String, Value> context = contexts.get(contexts.size() - 1);
        String name = this.variableNode.getName();
        if (context.get(name) != null) {
            throw new RunException();
        }
        context.put(name, UndefinedValue.create());
        return null;
    }
}
