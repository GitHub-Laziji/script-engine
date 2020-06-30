package org.laziji.commons.script.model;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;

public interface Node {

    String getSegment();

    Object run()  throws RunException;

    void compile() throws CompileException;

}
