package xyz.s4i5.quoteservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.s4i5.quoteservice.model.entity.ProcessedMessage;
import xyz.s4i5.quoteservice.model.query.client.message.SendMessageQuery;

import java.time.Instant;

@Mapper
public interface MessageMapper {

    @Mapping(source = "query.message", target = "message")
    @Mapping(source = "message", target = "sourceMessage")
    @Mapping(target = "processedAt", expression = "java(getCurrentTime())")
    ProcessedMessage toEntity(SendMessageQuery query, String message, Integer groupId, String sourceId);

    default Long getCurrentTime() {
        return Instant.now().getEpochSecond();
    }
}
