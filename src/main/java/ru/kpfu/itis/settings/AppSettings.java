package ru.kpfu.itis.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "project-name.app")
public class AppSettings {
    private String mainUrl;
    private String mainFrontUrl;
}
