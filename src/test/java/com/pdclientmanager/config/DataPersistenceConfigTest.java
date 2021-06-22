package com.pdclientmanager.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@EnableJpaRepositories(basePackages = "com.pdclientmanager.repository", entityManagerFactoryRef = "sessionFactory")
@EnableSpringDataWebSupport
public class DataPersistenceConfigTest {

    private Environment environment;
    
    @Autowired
    public DataPersistenceConfigTest(Environment environment) {
        this.environment = environment;
    }

    @Bean("sessionFactory")
    @Primary
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(jdbcDataSource());
        factoryBean.setPackagesToScan("com.pdclientmanager.repository.entity");
        factoryBean.setHibernateProperties(hibernateProperties());
        factoryBean.setPhysicalNamingStrategy(new SnakeCaseHibernateNamingStrategy());
        return factoryBean;
    }
    
    @Bean
    public DataSource jdbcDataSource() {
          DriverManagerDataSource dataSource = new DriverManagerDataSource();
          dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
          dataSource.setUrl("jdbc:tc:mysql://localhost:3306/walton_public_defender?TC_INITSCRIPT=databaseTest.sql?TC_DAEMON=true");
          dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
          dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
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
    
    @Bean("transactionManager")
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}