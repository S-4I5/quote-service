package xyz.s4i5.quoteservice.model.query.client.user;

import feign.Param;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class GetUsersQuery {
    private Integer userId;
    private String accessToken;
    private String version;
    // For some reason spring-data-redis breaks this annotation on fields
    @Param("user_ids")
    public Integer getUserId() {
        return userId;
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