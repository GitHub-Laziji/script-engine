package org.laziji.commons.script.model;

public interface Node {

    String getSegment();

    String run();

    boolean test();

    void init();

    boolean isInitialized();

}
