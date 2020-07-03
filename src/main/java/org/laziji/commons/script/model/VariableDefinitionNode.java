package org.laziji.commons.script.model;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;

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
    public Object run() throws RunException {
        return null;
    }
}
