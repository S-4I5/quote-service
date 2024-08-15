package xyz.s4i5.quoteservice.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.s4i5.quoteservice.client.VkMessageApiClient;
import xyz.s4i5.quoteservice.exceptions.VkApiException;
import xyz.s4i5.quoteservice.model.entity.VkUser;
import xyz.s4i5.quoteservice.model.query.client.message.SendMessageQuery;
import xyz.s4i5.quoteservice.service.SourceService;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkMessageService {
    private final VkMessageApiClient client;
    private final SourceService sourceService;
    private final Random random;

    public SendMessageQuery sendMessage(VkUser user, String sourceId, String message) {
        var info = sourceService.getCallbackSourceInfoById(sourceId);

        var query = SendMessageQuery.builder()
                .accessToken(info.getAccessToken())
                .message(MessageTextCustomizer.customize(user, message))
                .randomId(random.nextInt())
                .userId(user.getId())
                .version(info.getVersion())
                .build();

        log.info("Sending message '{}' back to {} in {}", message, user.getId(), sourceId);
        var responseDto = client.send(query);

        if (responseDto.getError() != null) {
            throw new VkApiException(responseDto.getError());
        }

        return query;
    }
}
