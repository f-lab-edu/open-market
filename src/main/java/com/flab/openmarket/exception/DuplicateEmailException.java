package com.flab.openmarket.exception;

public class DuplicateEmailException extends CustomException{
    public DuplicateEmailException() {
        super(MemberErrorCode.DUPLICATE_EMAIL);
    }
}
