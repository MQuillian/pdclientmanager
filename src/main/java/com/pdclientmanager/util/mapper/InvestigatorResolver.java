package com.pdclientmanager.util.mapper;

import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.entity.Investigator;

@Component
public class InvestigatorResolver {

    GenericEmployeeDaoImpl<Investigator> dao;
    
    @Autowired
    public InvestigatorResolver(GenericEmployeeDaoImpl<Investigator> dao) {
        this.dao = dao;
        this.dao.setClass(Investigator.class);
    }
    
    @Transactional
    public Investigator resolve(Long investigatorId, @TargetType Class<Investigator> type) {
        return investigatorId != null ? dao.loadProxy(investigatorId) : new Investigator();
    }
    
    public Long toLong(Investigator entity) {
        return entity != null ? entity.getId() : null;
    }
}
