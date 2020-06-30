package org.laziji.commons.script.exception;

public class CompileException extends Exception {

    public CompileException() {
        super();
    }

    public CompileException(String format, Object... args) {
        super(String.format(format, args));
    }
}
