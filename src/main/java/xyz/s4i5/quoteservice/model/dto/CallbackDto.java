package xyz.s4i5.quoteservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.s4i5.quoteservice.model.CallbackType;

@Getter
@Setter
@Builder
public class CallbackDto {
    @NotNull
    private CallbackType type;
    private JsonNode object;
    @JsonProperty("group_id")
    @PositiveOrZero
    private Integer groupId;
    @NotNull
    private String secret;
}