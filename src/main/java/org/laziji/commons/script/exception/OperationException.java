package org.laziji.commons.script.exception;

public class OperationException extends Exception {

    public OperationException() {
        super();
    }

    public OperationException(String format, Object... args) {
        super(String.format(format, args));
    }
}
