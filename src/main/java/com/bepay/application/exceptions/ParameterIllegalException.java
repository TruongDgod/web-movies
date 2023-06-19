package com.bepay.application.exceptions;

/**
 * @see ParameterIllegalException
 *
 * @author vidal
 * @return ParameterIllegalException
 */
public class ParameterIllegalException extends BaseException{
    private static final long serialVersionUID = 1L;

    private static final int ERROR_STATUS_DEFAULT = 400;

    ParameterIllegalException(String message, Throwable e) {
        super(ERROR_STATUS_DEFAULT, message, e);
    }

    public ParameterIllegalException(String message) {
        super(ERROR_STATUS_DEFAULT, message);
    }
}