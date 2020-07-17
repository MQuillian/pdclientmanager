package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.AttorneyForm;
import com.pdclientmanager.model.projection.AttorneyLightProjection;
import com.pdclientmanager.model.projection.AttorneyProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.util.mapper.AttorneyMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class AttorneyServiceImpl implements AttorneyService {

    private AttorneyRepository repository;
    private AttorneyMapper mapper;
    
    @Autowired
    public AttorneyServiceImpl(AttorneyRepository repository,
            AttorneyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Long save(AttorneyForm form) {
        Attorney entity = mapper.toAttorneyFromAttorneyFormDto(form, new CycleAvoidingMappingContext());
        if(entity.getWorkingStatus().equals(WorkingStatus.INACTIVE)) {
            entity.setInvestigator(null);
        }
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <T> T findById(Long targetId, Class<T> type) {
        T attorney = repository.findById(targetId, type)
                .orElseThrow(EntityNotFoundException::new);
        return attorney;
    }
    
    @Override
    @Transactional
    public AttorneyForm findFormById(Long targetId) {
        Attorney entity = repository.findById(targetId, Attorney.class)
                .orElseThrow(EntityNotFoundException::new);
        AttorneyForm form = mapper.toAttorneyFormDtoFromAttorney(entity);
        return form;
    }

    @Override
    @Transactional
    public List<AttorneyProjection> findAll() {
        List<AttorneyProjection> attorneys = repository.findAllProjectedBy();
        
        return attorneys;
    }

    @Override
    @Transactional
    public List<AttorneyLightProjection> findAllActive() {
        List<AttorneyLightProjection> activeAttorneys = repository.findByWorkingStatus(WorkingStatus.ACTIVE, AttorneyLightProjection.class);
        return activeAttorneys;
    }

    @Override
    @Transactional
    public boolean delete(AttorneyProjection attorney) {
        return deleteById(attorney.getId());
    }

    @Override
    @Transactional
    public boolean deleteById(Long targetId) {
        Attorney entity = repository.findById(targetId, Attorney.class)
                .orElseThrow(EntityNotFoundException::new);
        if(entity.getCaseload().isEmpty()) {
            repository.delete(entity);
            return true;
        } else {
            return false;
        }
    }
    
    

}
