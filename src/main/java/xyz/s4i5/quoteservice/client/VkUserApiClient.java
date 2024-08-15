package xyz.s4i5.quoteservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.s4i5.quoteservice.model.dto.client.user.GetUsersResponseDto;
import xyz.s4i5.quoteservice.model.query.client.user.GetUsersQuery;

@FeignClient(
        name = "vkUserApiClient",
        url = "${vk.uri}",
        path = "/method"
)
public interface VkUserApiClient {
    String GET_URI = "users.get";

    @PostMapping(GET_URI)
    GetUsersResponseDto get(@SpringQueryMap GetUsersQuery query);
}