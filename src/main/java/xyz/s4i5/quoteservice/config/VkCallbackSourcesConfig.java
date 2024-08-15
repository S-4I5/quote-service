package xyz.s4i5.quoteservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xyz.s4i5.quoteservice.model.CallbackSourceInfo;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "vk")
public class VkCallbackSourcesConfig {
    private Map<String, CallbackSourceInfo> sources;
}