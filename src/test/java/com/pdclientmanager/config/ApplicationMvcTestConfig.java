package com.pdclientmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.pdclientmanager"})
public class ApplicationMvcTestConfig extends ApplicationMvcConfig {
    
    //Override/implement any test specific application configuration here
}
