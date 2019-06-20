package com.pdclientmanager.config;

import com.pdclientmanager.config.AppInitializer;

public class AppTestInitializer extends AppInitializer {

    //Override/implement any test specific initialization here
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {HibernateTestConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ApplicationMvcTestConfig.class};
    }
}
