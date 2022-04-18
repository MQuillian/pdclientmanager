package com.pdclientmanager.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pdclientmanager.model.projection.OfficeStatsDto;

@Component
public class AdministratorDao {
    @Autowired
    @Qualifier("sessionFactory")
    private EntityManagerFactory emf;
    // NOTE: This class is solely for resetting data to an original state for demo purposes
    @org.springframework.transaction.annotation.Transactional
    public OfficeStatsDto getOfficeStats() {
        EntityManager em = emf.createEntityManager();
        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("CALC_OFFICE_STATS");
            sp.registerStoredProcedureParameter("@total", Integer.class, ParameterMode.OUT);
            sp.registerStoredProcedureParameter("@inCustody", Integer.class, ParameterMode.OUT);
            sp.registerStoredProcedureParameter("@totalAvg", Double.class, ParameterMode.OUT);
            sp.registerStoredProcedureParameter("@inCustodyAvg", Double.class, ParameterMode.OUT);
            sp.execute();
            
            return new OfficeStatsDto(
                    (int)sp.getOutputParameterValue("@total"),
                    (int)sp.getOutputParameterValue("@inCustody"),
                    (double)sp.getOutputParameterValue("@totalAvg"),
                    (double)sp.getOutputParameterValue("@inCustodyAvg"));
        } finally {
            em.close();
        }
        
    }
}
