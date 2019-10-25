package com.pdclientmanager.util.mapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.repository.AttorneyRepository;

@Component
public class AttorneyResolver {

    AttorneyRepository repository;
    
    @Autowired
    public AttorneyResolver(AttorneyRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public Attorney resolve(Long attorneyId, @TargetType Class<Attorney> type) {
        if(attorneyId != null) {
            return repository.findById(attorneyId).orElseThrow(EntityNotFoundException::new);
        } else {
            return new Attorney();
        }
    }
    
    public Long toLong(Attorney entity) {
        return entity != null ? entity.getId() : null;
    }
}
