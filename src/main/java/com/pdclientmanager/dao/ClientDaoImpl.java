package com.pdclientmanager.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pdclientmanager.model.Attorney;
import com.pdclientmanager.model.Client;

@Repository
public class ClientDaoImpl implements CrudDao<Client> {
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Client newClient) {
        sessionFactory.getCurrentSession().save(newClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Client> search(String searchTerm) {
        Session session = sessionFactory.getCurrentSession();
        Query searchQuery = session.createQuery("FROM Client WHERE name LIKE :searchTerm");
        searchQuery.setParameter("searchTerm", "%" + searchTerm + "%");
        return searchQuery.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Client> list() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Client> listQuery = session.createQuery("FROM Client");
        return listQuery.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Client> listFilteredByAttorney(Attorney attorney) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Client> filteredListQuery = session.createQuery("FROM Client WHERE attorney_id=:attorneyId");
        filteredListQuery.setParameter("attorney_id", attorney.getId());
        return filteredListQuery.getResultList();
    }

    @Override
    public void update(Long targetId, Client updatedClient) {
        Session session = sessionFactory.getCurrentSession();
        Client loadedClient = session.load(Client.class, targetId);
        session.merge(updatedClient);
        
    }

    @Override
    public void delete(Long targetId) {
        Session session = sessionFactory.getCurrentSession();
        Query deleteQuery = session.createQuery("DELETE FROM Client WHERE id=:targetId");
        deleteQuery.setParameter("targetId", targetId);
        deleteQuery.executeUpdate();
    }

    @Override
    public Client getById(Long targetId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    

}
