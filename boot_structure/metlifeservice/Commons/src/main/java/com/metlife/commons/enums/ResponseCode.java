package com.metlife.commons.enums;


import com.metlife.commons.models.response.Response;

public enum ResponseCode {
    // Never user 111 for response code

    OPERATION_SUCCESSFUL(100),
    RECORD_NOT_FOUND(101),
    AUTHENTICATION_FAILED(1102),
    INVALID_ARGUMENT(103),
    INVALID_TRANSACTION_PASSWORD(104),
    UNEXPECTED_EXCEPTION(105),
    SECURITY_ERROR(106),
    DATABASE_ERROR(110),
    RUNTIME_ERROR(500),
    REMOTE_ERROR(502),
    INTERNAL_CONNECTION_ERROR(503),
    FILE_UPLOAD_ERROR(504),
    INTERNAL_REMOTE_ERROR(1501),
    CORPORATE_CUSTOMER(701),
    INVALID_OTP(104);

    private int code;

    private ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isOperationSuccessful(Response response) {
        return response != null && response.getResponseCode() == ResponseCode.OPERATION_SUCCESSFUL.getCode();
    }

    public static boolean isNotOperationSuccessful(Response response) {
        return !isOperationSuccessful(response);
    }

}
