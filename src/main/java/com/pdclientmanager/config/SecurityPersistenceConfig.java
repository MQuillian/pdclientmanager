package com.pdclientmanager.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.pdclientmanager.security", entityManagerFactoryRef = "securitySessionFactory")
@EnableSpringDataWebSupport
public class SecurityPersistenceConfig {

    private Environment environment;
    
    @Autowired
    public SecurityPersistenceConfig(Environment environment) {
        this.environment = environment;
    }
    
    @Bean("securitySessionFactory")
    @Primary
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(securityDataSource());
        factoryBean.setPackagesToScan("com.pdclientmanager.security");
        factoryBean.setHibernateProperties(hibernateProperties());
        factoryBean.setPhysicalNamingStrategy(new SnakeCaseHibernateNamingStrategy());
        return factoryBean;
    }
    
    @Bean
    DataSource securityDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("security.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("security.url"));
        dataSource.setUsername(environment.getRequiredProperty("security.username"));
        dataSource.setPassword(environment.getRequiredProperty("security.password"));
        return dataSource;
    }
    
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("hibernate.c3p0.min_size"));
        properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("hibernate.c3p0.max_size"));
        properties.put("hibernate.c3p0.acquire_increment", environment.getRequiredProperty("hibernate.c3p0.acquire_increment"));
        properties.put("hibernate.c3p0.acquireRetryDelay", environment.getRequiredProperty("hibernate.c3p0.acquireRetryDelay"));
        properties.put("hibernate.c3p0.max_statements", environment.getRequiredProperty("hibernate.c3p0.max_statements"));
        return properties;
    }
}
