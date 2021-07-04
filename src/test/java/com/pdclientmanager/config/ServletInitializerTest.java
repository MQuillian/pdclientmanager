 package com.pdclientmanager.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletInitializerTest extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {DataPersistenceConfigTest.class, SecurityConfigTest.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfigTest.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
