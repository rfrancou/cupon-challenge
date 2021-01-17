package com.meli.coupon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectItemPriceException extends RuntimeException {

    public IncorrectItemPriceException(String message) {
        super(message);
    }
}
