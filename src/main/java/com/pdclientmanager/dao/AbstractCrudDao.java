package com.pdclientmanager.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCrudDao<T extends Serializable> {

    private Class<T> entityClass;
    
    @Autowired
    private SessionFactory sessionFactory;
    

    public final void setClass(Class<T> classToSet) {
        entityClass = classToSet;
    }
    
    public void create(final T entity) {
        getCurrentSession().persist(entity);
    }
    
    public T getById(final Long id) {
        return (T) getCurrentSession().get(entityClass, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getCurrentSession()
                .createQuery("FROM " + entityClass.getName()).getResultList();
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
}
