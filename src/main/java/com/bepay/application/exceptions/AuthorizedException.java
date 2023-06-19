/*******************************************************************************
 * Class        LoggedException
 * Created date 23/11/2021
 * Lasted date
 * Author       thanhsang1999
 * Change log   23/11/2021 thanhsang1999 Create New
 ******************************************************************************/
package com.bepay.application.exceptions;

import java.util.HashMap;

/**
 * @author thanhsang1999
 * @see AuthorizedException
 */
public class AuthorizedException extends BaseException {
    private static final long serialVersionUID = 1L;

    private static final int ERROR_STATUS_DEFAULT = 401;

    public AuthorizedException(String message, Throwable e) {
        super(ERROR_STATUS_DEFAULT, message, e);
    }

    public AuthorizedException(String message) {
        super(ERROR_STATUS_DEFAULT, message);
    }

    public AuthorizedException(HashMap<String, String> errMap) {
        super(ERROR_STATUS_DEFAULT, errMap);
    }
}
