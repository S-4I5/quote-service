package xyz.s4i5.quoteservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.s4i5.quoteservice.client.VkGroupApiClient;
import xyz.s4i5.quoteservice.config.VkCallbackSourcesConfig;
import xyz.s4i5.quoteservice.exceptions.QuoteServiceCoreException;
import xyz.s4i5.quoteservice.exceptions.SecretValidationFailedException;
import xyz.s4i5.quoteservice.exceptions.VkApiException;
import xyz.s4i5.quoteservice.model.CallbackSourceInfo;
import xyz.s4i5.quoteservice.model.query.client.group.GetCallbackConfirmationCodeQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceService {
    private final VkCallbackSourcesConfig config;
    private final VkGroupApiClient client;

    public String getCallbackConfirmationCode(String sourceId, Integer groupId) {
        var info = config.getSources().get(sourceId);

        var query = GetCallbackConfirmationCodeQuery.builder()
                .accessToken(info.getAccessToken())
                .version(info.getVersion())
                .groupId(groupId)
                .build();

        var responseDto = client.getCallbackConfirmationCode(query);

        if (responseDto.getError() != null) {
            throw new VkApiException(responseDto.getError());
        }

        return responseDto.getResponse().getCode();
    }

    public void validateSecretForSource(String sourceId, String secret) {
        var info = config.getSources().get(sourceId);

        if (info == null || !info.getSecret().equals(secret)) {
            log.error("Validation for {} with secret '{}' failed", sourceId, secret);
            throw new SecretValidationFailedException();
        }
    }

    public CallbackSourceInfo getCallbackSourceInfoById(String sourceId) {
        var info = config.getSources().get(sourceId);

        if (info == null) {
            log.error("Failed while looking for info for {}", sourceId);
            throw new QuoteServiceCoreException("");
        }

        return info;
    }
}