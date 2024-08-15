package xyz.s4i5.quoteservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallbackSourceInfo {
    private String version;
    private String accessToken;
    private String secret;
}
