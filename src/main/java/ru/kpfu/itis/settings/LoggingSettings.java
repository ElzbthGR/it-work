package ru.kpfu.itis.settings;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorProperties;
import com.github.gavlyukovskiy.boot.jdbc.decorator.dsproxy.DataSourceProxyProperties;
import com.github.gavlyukovskiy.boot.jdbc.decorator.p6spy.P6SpyProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "logging")
public class LoggingSettings {

    private static final String SQL_LOG_FORMAT = "[time=%(executionTime)ms, cid=%(connectionId)] %(sqlSingleLine)";
    private static final Long DEFAULT_SLOW_SQL_THRESHOLD = 300L;

    /**
     * Enable logging metadata to the log.
     */
    private Boolean showMetadata;

    /**
     * Enable logging request execution time to the log.
     */
    private Boolean showRequestExecutionTime;

    /**
     * Enable logging sql-queries to the log.
     */
    private Boolean showSql;

    /**
     * Enable logging queries count to the log.
     */
    private Boolean showQueryCount;

    /**
     * Enable logging slow queries to the log.
     */
    private Boolean showSlowSql;

    /**
     * Number of seconds to consider query as slow.
     */
    private Long slowSqlThreshold = DEFAULT_SLOW_SQL_THRESHOLD;

    @Bean
    @Primary
    @ConditionalOnBean(LoggingSettings.class)
    public DataSourceDecoratorProperties dataSourceDecoratorProperties() {
        DataSourceDecoratorProperties dataSourceDecoratorProperties = new DataSourceDecoratorProperties();
        dataSourceDecoratorProperties.setEnabled(showSql || showSlowSql);
        dataSourceDecoratorProperties.setP6spy(p6SpyProperties());
        dataSourceDecoratorProperties.setDatasourceProxy(dataSourceProxyProperties());
        return dataSourceDecoratorProperties;
    }

    private P6SpyProperties p6SpyProperties() {
        P6SpyProperties p6SpyProperties = new P6SpyProperties();
        p6SpyProperties.setEnableLogging(showSql);
        p6SpyProperties.setLogFormat(SQL_LOG_FORMAT);
        return p6SpyProperties;
    }

    private DataSourceProxyProperties dataSourceProxyProperties() {
        DataSourceProxyProperties dataSourceProxyProperties = new DataSourceProxyProperties();
        dataSourceProxyProperties.setMultiline(false);
        DataSourceProxyProperties.SlowQuery slowQuery = dataSourceProxyProperties.getSlowQuery();
        slowQuery.setEnableLogging(showSlowSql);
        slowQuery.setThreshold(slowSqlThreshold);
        return dataSourceProxyProperties;
    }
}
