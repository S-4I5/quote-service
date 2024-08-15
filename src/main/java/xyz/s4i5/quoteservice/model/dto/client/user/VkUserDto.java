package xyz.s4i5.quoteservice.model.dto.client.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VkUserDto {
    private Integer id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String secondName;
    @JsonProperty("is_closed")
    private String isClosed;
}