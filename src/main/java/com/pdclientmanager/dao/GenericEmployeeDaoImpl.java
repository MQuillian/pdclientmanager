package com.pdclientmanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericEmployeeDaoImpl<T>
    extends AbstractCrudDao<T> {
    
    //Superclass has all basic CRUD methods: create, getById, getAll,
    //   getAllActive, update, delete, and deleteById
    
    public List<T> getAllActive() {
        Class<T> entityClass = this.getEntityClass();
        TypedQuery<T> query = getCurrentSession()
                .createQuery("FROM " + entityClass.getName() + " WHERE employment_status = 0", entityClass);
        return query.getResultList();
    }
}

