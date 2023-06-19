package com.bepay.application.exceptions;

/**
 * @see SystemErrorException
 *
 * @author vidal
 * @return SystemErrorException
 */
public class SystemErrorException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final int ERROR_STATUS_DEFAULT = 500;

    public SystemErrorException(String message, Throwable e) {
        super(ERROR_STATUS_DEFAULT, message, e);
    }

    public SystemErrorException(String message) {
        super(ERROR_STATUS_DEFAULT, message);
    }
}