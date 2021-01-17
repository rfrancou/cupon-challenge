package com.meli.coupon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InsufficientAmountException extends RuntimeException {

    public InsufficientAmountException() {
    }
}
