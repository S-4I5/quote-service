package xyz.s4i5.quoteservice.model.query.client.message;

import feign.Param;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class SendMessageQuery {
    private String message;
    private Integer peerId;
    private Integer userId;
    private Integer randomId;
    private String accessToken;
    private String version;

    public String getMessage() {
        return message;
    }
    // For some reason spring-data-redis breaks this annotation on fields
    @Param("user_id")
    public Integer getPeerId() {
        return peerId;
    }
    @Param("user_id")
    public Integer getUserId() {
        return userId;
    }
    @Param("random_id")
    public Integer getRandomId() {
        return randomId;
    }
    @Param("access_token")
    public String getAccessToken() {
        return accessToken;
    }
    @Param("v")
    public String getVersion() {
        return version;
    }
}