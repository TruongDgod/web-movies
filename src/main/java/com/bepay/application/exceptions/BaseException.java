package com.bepay.application.exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @see BaseException
 *
 * @author vidal
 * @return BaseException
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final int UNKNOWN = -1;

    private HttpStatus httpStatus;
    private Object errorDetails;

    private HashMap<String, String> errMap;

    public BaseException(int error, String message, Throwable e) {
        super(message, e);
        this.httpStatus = HttpStatus.valueOf(error);
    }

    public BaseException(String message, Throwable e) {
        this(UNKNOWN, message, e);
    }

    public BaseException(int error, String message) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(error);
    }

    public BaseException(String message) {
        this(UNKNOWN, message);
    }

    public BaseException(int error, HashMap<String, String> errMap) {
        this.errMap = errMap;
        this.httpStatus = HttpStatus.valueOf(error);
    }

    public HashMap<String, String> getErrMap() {
        return errMap;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Object errorDetails) {
        this.errorDetails = errorDetails;
    }


}
