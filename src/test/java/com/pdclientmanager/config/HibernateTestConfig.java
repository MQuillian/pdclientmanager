package com.pdclientmanager.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pdclientmanager.config.HibernateConfig;


@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.pdclientmanager"
})
public class HibernateTestConfig extends HibernateConfig {
    
    //Override/implement any test specific Hibernate configuration here
    
    @Override
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/walton_public_defender");
        dataSource.setUsername("test");
        dataSource.setPassword("test");
        return dataSource;
      }
}