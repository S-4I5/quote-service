package xyz.s4i5.quoteservice.model.dto.client.user;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetUsersResponseDto {
    List<VkUserDto> response;
    private JsonNode error;
}
