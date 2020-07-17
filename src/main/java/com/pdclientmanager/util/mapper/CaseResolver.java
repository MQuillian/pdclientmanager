package com.pdclientmanager.util.mapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.repository.entity.Case;

@Component
public class CaseResolver {
    
CaseRepository repository;
    
    @Autowired
    public CaseResolver(CaseRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public Case resolve(Long attorneyId, @TargetType Class<Case> type) {
        if(attorneyId != null) {
            return repository.findById(attorneyId).orElseThrow(EntityNotFoundException::new);
        } else {
            return new Case();
        }
    }
    
    public Long toLong(Case entity) {
        return entity != null ? entity.getId() : null;
    }

}
