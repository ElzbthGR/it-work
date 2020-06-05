package ru.kpfu.itis.configs;

import ru.kpfu.itis.logging.QueryCountInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.INTERCEPTOR;

@Component
@RequiredArgsConstructor
public class InterceptorHibernatePropertiesCustomizer implements HibernatePropertiesCustomizer {

    private final QueryCountInterceptor queryCountInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(INTERCEPTOR, queryCountInterceptor);
    }
}
