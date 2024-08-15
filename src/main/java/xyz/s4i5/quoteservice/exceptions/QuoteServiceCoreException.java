package xyz.s4i5.quoteservice.exceptions;

import lombok.Getter;

@Getter
public class QuoteServiceCoreException extends RuntimeException {
    private final String messageCode;

    public QuoteServiceCoreException(String messageCode) {
        this.messageCode = messageCode;
    }
}
