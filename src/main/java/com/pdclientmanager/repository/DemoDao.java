package com.pdclientmanager.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DemoDao {
    @Autowired
    @Qualifier("sessionFactory")
    private EntityManagerFactory emf;
    // NOTE: This class is solely for resetting data to an original state for demo purposes
    @Transactional
    public void resetData() {
        EntityManager em = emf.createEntityManager();
        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("RESET_DEMO_DATA");
            sp.execute();
        } finally {
            em.close();
        }
        
    }
}