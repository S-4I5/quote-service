package xyz.s4i5.quoteservice.model.dto.client.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCallbackConfirmationCodeResponse {
    private ConfirmationCodeDto response;
    private JsonNode error;
}