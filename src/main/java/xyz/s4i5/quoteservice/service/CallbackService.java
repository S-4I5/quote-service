package xyz.s4i5.quoteservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.s4i5.quoteservice.mapper.MessageMapper;
import xyz.s4i5.quoteservice.model.Message;
import xyz.s4i5.quoteservice.model.MessageNewObject;
import xyz.s4i5.quoteservice.model.dto.CallbackDto;
import xyz.s4i5.quoteservice.model.entity.ProcessedMessage;
import xyz.s4i5.quoteservice.model.entity.VkUser;
import xyz.s4i5.quoteservice.repository.ProcessedMessageRepository;
import xyz.s4i5.quoteservice.service.message.VkMessageService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackService {
    private static final String RESULT_OK = "ok";

    private final ObjectMapper mapper;
    private final MessageMapper messageMapper;
    private final SourceService sourceService;
    private final VkMessageService vkMessageService;
    private final VkUserService vkUserService;
    private final ProcessedMessageRepository repository;

    public String process(CallbackDto callbackDto, String sourceId) {
        log.info("Received callback from {} with secret '{}' with body '{}'", sourceId, callbackDto.getSecret(), callbackDto.getObject());

        sourceService.validateSecretForSource(sourceId, callbackDto.getSecret());

        return switch (callbackDto.getType()) {
            case CONFIRMATION -> processConfirmationCallback(sourceId, callbackDto.getGroupId());
            case MESSAGE_NEW -> processNewMessageCallback(callbackDto, sourceId);
        };
    }

    private String processConfirmationCallback(String sourceId, Integer groupId) {
        log.info("Returning secret to {}", sourceId);
        return sourceService.getCallbackConfirmationCode(sourceId, groupId);
    }

    @SneakyThrows
    private String processNewMessageCallback(CallbackDto callbackDto, String sourceId) {
        Message message = mapper.treeToValue(callbackDto.getObject(), MessageNewObject.class).getMessage();

        VkUser user = vkUserService.getUserById(message.getFromId(), sourceId);

        var res = vkMessageService.sendMessage(
                user,
                sourceId,
                message.getText()
        );

        ProcessedMessage processedMessage = messageMapper
                .toEntity(res, message.getText(), callbackDto.getGroupId(), sourceId);

        log.info("Saving processed message '{}'", processedMessage.toString());
        repository.save(processedMessage);

        return RESULT_OK;
    }
}