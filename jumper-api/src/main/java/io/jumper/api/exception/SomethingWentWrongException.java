package io.jumper.api.exception;

public class SomethingWentWrongException extends RuntimeException {

    public SomethingWentWrongException() {
    }

    public SomethingWentWrongException(String message) {
        super(message);
    }

    public SomethingWentWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public SomethingWentWrongException(Throwable cause) {
        super(cause);
    }

    public SomethingWentWrongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
