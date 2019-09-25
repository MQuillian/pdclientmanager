package com.pdclientmanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.pdclientmanager.model.entity.Case;

@Repository
public class CaseDaoImpl extends AbstractCrudDao<Case> {

    //Superclass has all basic CRUD methods: create, getById, getAll,
    //   getAllActive, update, delete, and deleteById
    
    public List<Case> getAllWithInitializedClients() {
        TypedQuery<Case> query = getCurrentSession()
                .createQuery("FROM Case As c "
                        + "LEFT JOIN FETCH c.client ", Case.class);
        return query.getResultList();
    }
    
    public List<Case> getAllActiveByAttorneyId(Long attorneyId) {
        TypedQuery<Case> query = getCurrentSession()
                .createQuery("FROM Case AS c "
                        + "LEFT JOIN FETCH c.client "
                        + "WHERE attorney = " + attorneyId +
                        " AND case_status = 0", Case.class);
        return query.getResultList();
    }
}
