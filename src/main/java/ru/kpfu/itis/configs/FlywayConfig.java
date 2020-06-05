package ru.kpfu.itis.configs;

import ru.kpfu.itis.settings.FlywaySettings;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private final FlywaySettings flywaySettings;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .outOfOrder(true)
                .baselineOnMigrate(true)
                .dataSource(dataSource)
                .locations(flywaySettings.getPath())
                .load();
    }
}

