package xyz.s4i5.quoteservice.model.query.client.group;

import feign.Param;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class GetCallbackConfirmationCodeQuery {
    private Integer groupId;
    private String accessToken;
    private String version;
    // For some reason spring-data-redis breaks this annotation on fields
    @Param("group_id")
    public Integer getGroupId() {
        return groupId;
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
