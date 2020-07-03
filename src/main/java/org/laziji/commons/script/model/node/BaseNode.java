package org.laziji.commons.script.model.node;

public abstract class BaseNode implements Node {

    protected String segment;

    public BaseNode(String segment){
        this.segment = segment.trim();
    }

    @Override
    public String getSegment() {
        return segment;
    }
}
