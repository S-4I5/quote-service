package xyz.s4i5.quoteservice.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CallbackType {
    CONFIRMATION("confirmation"),
    MESSAGE_NEW("message_new");

    private final String name;

    CallbackType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}