package xyz.s4i5.quoteservice.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.s4i5.quoteservice.model.query.client.message.SendMessageQuery;

@SpringBootTest(
        classes = {
                MessageMapperImpl.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
class MessageMapperTest {

    @Autowired
    private MessageMapper mapper;

    @Test
    void toEntity() {
        var query = SendMessageQuery.builder()
                .accessToken("sometoken")
                .message("customized message")
                .randomId(1)
                .userId(1)
                .version("v1")
                .build();


        var actual = mapper.toEntity(query, "message", 1, "testGroup");


        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("sourceMessage", "message")
                .hasFieldOrPropertyWithValue("message", "customized message")
                .hasFieldOrPropertyWithValue("userId", 1)
                .hasFieldOrPropertyWithValue("randomId", 1)
                .hasFieldOrPropertyWithValue("groupId", 1)
                .hasFieldOrPropertyWithValue("sourceId", "testGroup")
                .hasNoNullFieldsOrPropertiesExcept("id");
    }
}
