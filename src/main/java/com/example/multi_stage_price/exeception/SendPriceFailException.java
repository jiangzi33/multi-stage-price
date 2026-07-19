package com.example.multi_stage_price.exeception;

public class SendPriceFailException extends RuntimeException {
    public SendPriceFailException(String message) {
        super(message);
    }
}
