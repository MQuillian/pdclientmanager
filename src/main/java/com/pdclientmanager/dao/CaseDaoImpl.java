package com.pdclientmanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.pdclientmanager.model.Case;

public class CaseDaoImpl extends AbstractCrudDao<Case> {

    //Superclass has all basic CRUD methods: create, getById, getAll,
    //   getAllActive, update, delete, and deleteById
    
    public List<Case> getCasesByAttorneyId(Long attorneyId) {
        TypedQuery<Case> query = getCurrentSession()
                .createQuery("FROM cases WHERE attorney = " + attorneyId, Case.class);
        return query.getResultList();
    }
}
