package org.laziji.commons.script.model;

import org.laziji.commons.script.exception.CompileException;

public interface Node {

    String getSegment();

    Object run()  throws CompileException;

    void compile() throws CompileException;

}
