package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;

import java.util.List;
import java.util.Map;

public interface Node {

    void compile() throws CompileException;

    Object run(List<Map<String, Value>> contexts) throws RunException;

    String getSegment();
}
