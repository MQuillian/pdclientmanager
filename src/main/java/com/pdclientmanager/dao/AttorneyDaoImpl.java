package com.pdclientmanager.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pdclientmanager.model.Attorney;

@Repository
public class AttorneyDaoImpl implements CrudDao<Attorney> {
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Attorney newAttorney) {
        sessionFactory.getCurrentSession().save(newAttorney);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Attorney> search(String searchTerm) {
        Session session = sessionFactory.getCurrentSession();
        Query searchQuery = session.createQuery("FROM Attorney WHERE name LIKE :searchTerm");
        searchQuery.setParameter("searchTerm", "%" + searchTerm + "%");
        return searchQuery.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Attorney> list() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Attorney> listQuery = session.createQuery("FROM Attorney");
        return listQuery.getResultList();
    }

    @Override
    public void update(Long targetId, Attorney updatedAttorney) {
        Session session = sessionFactory.getCurrentSession();
        Attorney existingAttorney = session.load(Attorney.class, targetId);
        existingAttorney.setName(updatedAttorney.getName());
        existingAttorney.setEmploymentStatus(updatedAttorney.getEmploymentStatus());
    }

    @Override
    public void delete(Long targetId) {
        Session session = sessionFactory.getCurrentSession();
        Query deleteQuery = session.createQuery("DELETE FROM Attorney WHERE id=:attorneyId");
        deleteQuery.setParameter("attorneyId", targetId);
        deleteQuery.executeUpdate();
    }
    
    @Override
    public Attorney getById(Long targetId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Attorney.class, targetId);
    }

}
