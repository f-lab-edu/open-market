package com.flab.openmarket.exception;

public class DuplicatePhoneException extends CustomException{
    public DuplicatePhoneException() {
        super(MemberErrorCode.DUPLICATE_PHONE);
    }
}
