package xyz.s4i5.quoteservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.s4i5.quoteservice.model.dto.client.message.SendMessageResponseDto;
import xyz.s4i5.quoteservice.model.query.client.message.SendMessageQuery;

@FeignClient(
        name = "vkMessageApiClient",
        url = "${vk.uri}",
        path = "/method"
)
public interface VkMessageApiClient {
    String SEND_URI = "messages.send";

    @PostMapping(SEND_URI)
    SendMessageResponseDto send(@SpringQueryMap SendMessageQuery query);
}