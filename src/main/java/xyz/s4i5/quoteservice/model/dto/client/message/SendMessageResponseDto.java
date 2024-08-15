package xyz.s4i5.quoteservice.model.dto.client.message;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendMessageResponseDto {
    private Integer response;
    private JsonNode error;
}
