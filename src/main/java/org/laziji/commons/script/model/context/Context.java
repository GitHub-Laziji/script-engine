package org.laziji.commons.script.model.context;

import org.laziji.commons.script.model.value.Value;

public interface Context {

    void put(String name, Value value);

    Value get(String name);

    void close();

    boolean isClose();

}
