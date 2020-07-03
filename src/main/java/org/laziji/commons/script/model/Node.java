package org.laziji.commons.script.model;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;

public interface Node {

    void compile() throws CompileException;

    Object run()  throws RunException;

    String getSegment();
}
