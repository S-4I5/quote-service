package xyz.s4i5.quoteservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.s4i5.quoteservice.client.VkUserApiClient;
import xyz.s4i5.quoteservice.exceptions.VkApiException;
import xyz.s4i5.quoteservice.mapper.VkUserMapper;
import xyz.s4i5.quoteservice.model.entity.VkUser;
import xyz.s4i5.quoteservice.model.query.client.user.GetUsersQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkUserService {
    private final VkUserApiClient client;
    private final SourceService sourceService;
    private final VkUserMapper mapper;

    @Cacheable(value = "userCache", key = "#userId")
    public VkUser getUserById(Integer userId, String sourceId) {
        var sourceInfo = sourceService.getCallbackSourceInfoById(sourceId);
        log.info("Searching for user with id: {}, source id {} and token {}", userId, sourceId, sourceInfo.getAccessToken());

        var responseDto = client.get(GetUsersQuery.builder()
                .userId(userId)
                .version(sourceInfo.getVersion())
                .accessToken(sourceInfo.getAccessToken())
                .build()
        );

        if (responseDto.getError() != null) {
            throw new VkApiException(responseDto.getError());
        }

        log.info("Found {}", responseDto);

        return mapper.toModel(responseDto.getResponse().get(0));
    }
}
