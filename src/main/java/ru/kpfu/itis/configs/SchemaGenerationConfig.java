package ru.kpfu.itis.configs;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;

import javax.persistence.spi.PersistenceUnitInfo;

import java.util.List;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;

@Configuration
// TODO: 08/10/2019 change this
@EntityScan("ru.kpfu.itis.models")
@AutoConfigureAfter({HibernateJpaAutoConfiguration.class})
public class SchemaGenerationConfig {

    @Bean
    public EntityScanPackages entityScanPackages(BeanFactory beanFactory) {
        return EntityScanPackages.get(beanFactory);
    }

    @Bean
    public Metadata getMetadata(StandardServiceRegistry standardServiceRegistry,
                                PersistenceUnitInfo persistenceUnitInfo) {
        final MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        final List<String> managedClassNames = persistenceUnitInfo.getManagedClassNames();
        for (String managedClassName : managedClassNames) {
            metadataSources.addAnnotatedClassName(managedClassName);
        }
        return metadataSources.buildMetadata();
    }

    @Bean
    public StandardServiceRegistry getStandardServiceRegistry(JpaProperties jpaProperties,
                                                              DataSourceProperties dataSourceProperties) {
        final Map<String, String> properties = jpaProperties.getProperties();
        return new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .applySetting(USER, dataSourceProperties.getUsername())
                .applySetting(PASS, dataSourceProperties.getPassword())
                .applySetting(URL, dataSourceProperties.getUrl())
                .applySetting(DRIVER, dataSourceProperties.getDriverClassName())
                .applySetting(IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getCanonicalName())
                .applySetting(PHYSICAL_NAMING_STRATEGY, SpringPhysicalNamingStrategy.class.getCanonicalName())
                .build();
    }

    @Bean
    public PersistenceUnitInfo getPersistenceUnitInfo(EntityScanPackages entityScanPackages) {
        final List<String> packagesToScan = entityScanPackages.getPackageNames();
        final DefaultPersistenceUnitManager persistenceUnitManager = new DefaultPersistenceUnitManager();
        String[] packagesToScanArr = packagesToScan.toArray(new String[0]);
        persistenceUnitManager.setPackagesToScan(packagesToScanArr);
        persistenceUnitManager.afterPropertiesSet();
        return persistenceUnitManager.obtainDefaultPersistenceUnitInfo();
    }
}
