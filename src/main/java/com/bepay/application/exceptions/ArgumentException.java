package com.bepay.application.exceptions;

/**
 * @see ArgumentException
 *
 * @author vidal
 * @return ArgumentException
 */
public class ArgumentException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final int ERROR_STATUS_DEFAULT = 412;

    ArgumentException(String message, Throwable e) {
        super(ERROR_STATUS_DEFAULT, message, e);
    }

    public ArgumentException(String message) {
        super(ERROR_STATUS_DEFAULT, message);
    }
}