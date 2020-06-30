package org.laziji.commons.script.exception;

public class RunException extends Exception {

    public RunException() {
        super();
    }

    public RunException(String format, Object... args) {
        super(String.format(format, args));
    }
}
