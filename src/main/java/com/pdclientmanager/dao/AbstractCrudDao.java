package com.pdclientmanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCrudDao<T> implements CrudDao<T> {

    private Class<T> entityClass;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public final void setClass(Class<T> classToSet) {
        this.entityClass = classToSet;
    }
    
    public void persist(final T entity) {
        getCurrentSession().persist(entity);
    }
    
    public T getById(final Long targetId) {
        return (T) getCurrentSession().get(entityClass, targetId);
    }
    
    public List<T> getAll() {
        TypedQuery<T> query = getCurrentSession()
                .createQuery("FROM " + entityClass.getName(), entityClass);
        return query.getResultList();
    }
    
    public void merge(final T entity) {
        getCurrentSession().merge(entity);
    }
    
    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }
    
    public void deleteById(final Long targetId) {
        T entity = getById(targetId);
        delete(entity);
    }
    
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    protected Class<T> getEntityClass() {
        return this.entityClass;
    }
}
