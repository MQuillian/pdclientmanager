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
    
    public void create(final T entity) {
        getCurrentSession().persist(entity);
    }
    
    public T getById(final Long id) {
        return (T) getCurrentSession().get(entityClass, id);
    }
    
    public List<T> getAll() {
        TypedQuery<T> query = getCurrentSession()
                .createQuery("FROM " + entityClass.getName(), entityClass);
        return query.getResultList();
    }
    
    public void update(final T entity) {
        getCurrentSession().merge(entity);
    }
    
    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }
    
    public void deleteById(final Long id) {
        T entity = getById(id);
        delete(entity);
    }
    
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    protected Class<T> getEntityClass() {
        return this.entityClass;
    }
}
