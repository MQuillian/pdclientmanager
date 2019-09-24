package com.pdclientmanager.util.mapper;

import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.dao.GenericEmployeeDaoImpl;
import com.pdclientmanager.model.entity.Attorney;

@Component
public class AttorneyResolver {

    GenericEmployeeDaoImpl<Attorney> dao;
    
    @Autowired
    public AttorneyResolver(GenericEmployeeDaoImpl<Attorney> dao) {
        this.dao = dao;
        this.dao.setClass(Attorney.class);
    }
    
    @Transactional
    public Attorney resolve(Long attorneyId, @TargetType Class<Attorney> type) {
        return attorneyId != null ? dao.getById(attorneyId) : new Attorney();
    }
    
    public Long toLong(Attorney entity) {
        return entity != null ? entity.getId() : null;
    }
}
