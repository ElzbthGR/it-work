package ru.kpfu.itis.configs;

import ru.kpfu.itis.logging.MDCFilter;
import ru.kpfu.itis.security.HeaderAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FiltersRegistrar {

    @Bean
    public FilterRegistrationBean<HeaderAuthenticationFilter> registerAuthenticationFilter(HeaderAuthenticationFilter filter) {
        return registerFilter(filter, 1);
    }

    @Bean
    public FilterRegistrationBean<MDCFilter> registerMDCFilter(MDCFilter filter) {
        return registerFilter(filter, 2);
    }

    private static <F extends Filter> FilterRegistrationBean<F> registerFilter(F filter, int order) {
        FilterRegistrationBean<F> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(order);
        registration.addUrlPatterns("/v1/*");
        return registration;
    }
}
