package com.mibarrio.api.exception;

import org.springframework.http.HttpStatus;

public class AccountPendingApprovalException extends ApiException {

    public AccountPendingApprovalException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
