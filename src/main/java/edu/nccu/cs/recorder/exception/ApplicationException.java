package edu.nccu.cs.recorder.exception;

import lombok.Getter;

public class ApplicationException extends RuntimeException {
    @Getter
    private String message;
    @Getter
    private Throwable cause;

    public ApplicationException(String message) {
        super(message);
        this.message = message;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.cause = cause;
        this.message = message;
    }
}
