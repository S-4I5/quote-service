package xyz.s4i5.quoteservice.controller;

import com.redis.testcontainers.RedisContainer;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import xyz.s4i5.quoteservice.repository.ProcessedMessageRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static xyz.s4i5.quoteservice.util.TestUtil.readJsonAsString;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class CallbackControllerTest {
    private final String BASE_FOLDER = "CallbackControllerTest/";
    private final String INIT_GROUP_ID = "initGroup";

    @Container
    @ServiceConnection
    private final static GenericContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:16")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "user")
            .withEnv("POSTGRES_USER", "postgres")
            .withEnv("POSTGRES_PASSWORD", "password");

    @Container
    @ServiceConnection
    private final static GenericContainer<?> redisContainer = new RedisContainer("redis:7.0")
            .withExposedPorts(6379);

    private static final MockWebServer MOCK_WEB_SERVER = new MockWebServer();

    @DynamicPropertySource
    static void setMockWebServerProperties(DynamicPropertyRegistry registry) {
        registry.add("vk.uri", () -> MOCK_WEB_SERVER.getHostName() + ":" + MOCK_WEB_SERVER.getPort());
    }

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ProcessedMessageRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @SneakyThrows
    void processNewMessageCallback() {
        var getUserResponse = readJson("/response/processCallbackGetUserResponse");
        MOCK_WEB_SERVER.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(getUserResponse)
        );
        var sendMessageResponse = readJson("/response/processCallbackSendMessageResponse");
        MOCK_WEB_SERVER.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(sendMessageResponse)
        );

        var givenJson = readJson("/request/processCallback");


        var actual = mockMvc.perform(
                post(CallbackController.ROOT_URI + CallbackController.PROCESS_URI, INIT_GROUP_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJson)
        );
        var actualFormDb = repository.findAll().get(0);


        actual
                .andExpect(status().isOk())
                .andExpect(content().bytes("ok".getBytes()));
        Assertions.assertThat(actualFormDb)
                .hasFieldOrPropertyWithValue("sourceMessage", "jj")
                .hasFieldOrPropertyWithValue("userId", 84682488)
                .hasFieldOrPropertyWithValue("groupId", 226935813)
                .hasFieldOrPropertyWithValue("sourceId", INIT_GROUP_ID)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @SneakyThrows
    void processGetConfirmationCodeCallback() {
        var getConfirmationCodeResponse = readJson("/response/processGetConfirmationCodeCallbackGetConfirmationCodeResponse");
        MOCK_WEB_SERVER.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(getConfirmationCodeResponse)
        );

        var givenJson = readJson("/request/processGetConfirmationCodeCallback");


        var actual = mockMvc.perform(
                post(CallbackController.ROOT_URI + CallbackController.PROCESS_URI, INIT_GROUP_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJson)
        );


        actual
                .andExpect(status().isOk())
                .andExpect(content().bytes("1234verySecret".getBytes()));
    }

    @Test
    @SneakyThrows
    void return403WhenSecretValidationFailed() {
        var givenJson = readJson("/request/return403WhenSecretValidationFailed");


        var actual = mockMvc.perform(
                post(CallbackController.ROOT_URI + CallbackController.PROCESS_URI, INIT_GROUP_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJson)
        );


        var expected = readJson("/response/return403WhenSecretValidationFailed");
        actual
                .andExpect(status().isForbidden())
                .andExpect(content().json(expected));
    }

    @Test
    @SneakyThrows
    void return400WhenVkApiError() {
        var getUserResponse = readJson("/response/return400WhenVkApiErrorGetUserResponse");
        MOCK_WEB_SERVER.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(getUserResponse)
        );

        var givenJson = readJson("/request/return400WhenVkApiError");


        var actual = mockMvc.perform(
                post(CallbackController.ROOT_URI + CallbackController.PROCESS_URI, INIT_GROUP_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJson)
        );


        var expected = readJson("/response/return400WhenVkApiError");
        actual
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expected));
    }

    private String readJson(String filename) throws Exception {
        return readJsonAsString(BASE_FOLDER + filename);
    }
}
