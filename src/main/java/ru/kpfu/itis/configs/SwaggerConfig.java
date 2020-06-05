package ru.kpfu.itis.configs;

import com.google.common.collect.Sets;
import ru.kpfu.itis.settings.SwaggerSettings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(SwaggerSettings swaggerSettings) {
        Docket api = new Docket(DocumentationType.SWAGGER_12)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.kpfu.itis.controllers"))
                .build()
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .apiInfo(apiInfo());
        final String host = swaggerSettings.getHost();
        if (StringUtils.isNotBlank(host)) {
            api.host(host);
        }
        final String pathMapping = swaggerSettings.getPathMapping();
        if (StringUtils.isNotBlank(pathMapping)) {
            api.pathMapping(pathMapping);
        }
        if (Boolean.TRUE.equals(swaggerSettings.getEnableHttps())) {
            api.protocols(Sets.newHashSet("https"));
        }

        return api;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IT Simulation REST API")
                .version("v1")
                .build();
    }
}