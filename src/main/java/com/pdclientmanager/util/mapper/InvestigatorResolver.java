package com.pdclientmanager.util.mapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.repository.InvestigatorRepository;
import com.pdclientmanager.repository.entity.Investigator;

@Component
public class InvestigatorResolver {

    InvestigatorRepository repository;
    
    @Autowired
    public InvestigatorResolver(InvestigatorRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public Investigator resolve(Long investigatorId, @TargetType Class<Investigator> type) {
        if(investigatorId != null) {
            return repository.findById(investigatorId).orElseThrow(EntityNotFoundException::new);
        } else {
            return new Investigator();
        }
    }
    
    public Long toLong(Investigator entity) {
        return entity != null ? entity.getId() : null;
    }
}
