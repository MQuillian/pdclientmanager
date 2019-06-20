package com.pdclientmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pdclientmanager.config.AppTestInitializer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppTestInitializer.class})
@SpringJUnitWebConfig(com.pdclientmanager.config.ApplicationMvcTestConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
@ActiveProfiles("test")
public class BaseControllerTest {
    
    @Autowired
    protected WebApplicationContext webAppContext;
    
    protected MockMvc mvc;
    
    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
}
