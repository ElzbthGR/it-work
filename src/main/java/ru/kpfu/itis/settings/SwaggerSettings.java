package ru.kpfu.itis.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerSettings {
    private String pathMapping;
    private String host;
    private Boolean enableHttps;
}
