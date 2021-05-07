package me.kalpha.trsubscribeservice.trmart.config;

import com.zaxxer.hikari.HikariDataSource;
import me.kalpha.trsubscribeservice.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "trmartEntityManagerFactory",
        transactionManagerRef = "trmartTransactionManager",
        basePackages = {"me.kalpha.trsubscribeservice.trmart.repository"}//repositories
)
@EnableTransactionManagement
public class TmartDataSourceConfig {
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "trmartDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "trmartDataSource")
    public DataSource dataSource(){
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "trmartEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean trmartEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings()
        );

        return builder
                .dataSource(dataSource())
                .packages("me.kalpha.trsubscribeservice.trmart.entity")
                .persistenceUnit(Constants.TRMART_UNIT_NAME)
                .properties(properties)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager trmartTransactionManager(
            final @Qualifier("trmartEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}