package xyz.s4i5.quoteservice.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class VkApiException extends RuntimeException {
    private final JsonNode exceptionTree;

    public VkApiException(JsonNode exceptionTree) {
        this.exceptionTree = exceptionTree;
    }
}