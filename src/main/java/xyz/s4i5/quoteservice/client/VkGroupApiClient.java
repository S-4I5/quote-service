package xyz.s4i5.quoteservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.s4i5.quoteservice.model.dto.client.group.GetCallbackConfirmationCodeResponse;
import xyz.s4i5.quoteservice.model.query.client.group.GetCallbackConfirmationCodeQuery;

@FeignClient(
        name = "vkGroupApiClient",
        url = "${vk.uri}",
        path = "/method"
)
public interface VkGroupApiClient {
    String GET_CALLBACK_CON_CODE_URI = "groups.getCallbackConfirmationCode";

    @PostMapping(GET_CALLBACK_CON_CODE_URI)
    GetCallbackConfirmationCodeResponse getCallbackConfirmationCode(@SpringQueryMap GetCallbackConfirmationCodeQuery query);
}
