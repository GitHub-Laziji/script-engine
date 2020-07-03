package org.laziji.commons.script.model;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;

public class VariableNode extends BaseNode {

    public VariableNode(String segment){
        super(segment);
    }

    @Override
    public void compile() throws CompileException {

    }

    @Override
    public Object run() throws RunException {
        return null;
    }
}
