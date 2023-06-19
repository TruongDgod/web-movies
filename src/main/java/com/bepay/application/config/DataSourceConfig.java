package com.bepay.application.config;

import com.bepay.application.utils.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.InvalidKeyException;

/**
 * @author vidal
 * @implNote Configure data sources for multiple databases
 * @return
 * @see DataSourceConfig
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(value = "com.bepay.application.config")
@EnableJpaRepositories(entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager", basePackages = {"com.bepay.application.repository.db"})
public class DataSourceConfig {

    @Value("${spring.datasource.jdbc-url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver.class}")
    private String driver;

    @Autowired
    private SecurityConfig securityConfig;

    @Primary
    @Bean(name = "db1DataSource")
    public DataSource dataSource() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(AESUtils.decrypt(url, securityConfig.getKey()));
        dataSourceBuilder.username(AESUtils.decrypt(username, securityConfig.getKey()));
        dataSourceBuilder.password(AESUtils.decrypt(password, securityConfig.getKey()));
//        dataSourceBuilder.url(url);
//        dataSourceBuilder.username(username);
//        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "db1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("db1DataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.bepay.application.entities.db").persistenceUnit("db")
                .build();
    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager db1TransactionManager(
            @Qualifier("db1EntityManagerFactory") EntityManagerFactory db1EntityManagerFactory) {
        return new JpaTransactionManager(db1EntityManagerFactory);
    }
}
