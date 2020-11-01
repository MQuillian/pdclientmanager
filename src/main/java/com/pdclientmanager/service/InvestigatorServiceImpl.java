package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.InvestigatorForm;
import com.pdclientmanager.model.projection.InvestigatorProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.InvestigatorRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Investigator;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@Service
public class InvestigatorServiceImpl implements InvestigatorService {
    
    private InvestigatorRepository repository;
    private AttorneyRepository attorneyRepository;
    private InvestigatorMapper mapper;
    
    @Autowired
    public InvestigatorServiceImpl(InvestigatorRepository repository,
            AttorneyRepository attorneyRepository, InvestigatorMapper mapper) {
        this.repository = repository;
        this.attorneyRepository = attorneyRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long save(InvestigatorForm form) {
        if(!form.isNew()) {
            removeUndesiredAttorneyAssignmentsIfUpdated(form);
        }
        Investigator entity = mapper.toInvestigatorFromInvestigatorForm(form, new CycleAvoidingMappingContext());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <T> T findById(Long targetId, Class<T> type) {
        T investigator = repository.findById(targetId, type)
                .orElseThrow(EntityNotFoundException::new);
        return investigator;
    }
    
    @Override
    @Transactional
    public InvestigatorForm findFormById(Long targetId) {
        Investigator entity = repository.findById(targetId, Investigator.class)
                .orElseThrow(EntityNotFoundException::new);
        InvestigatorForm form = mapper.toInvestigatorFormFromInvestigator(entity);
        return form;
    }

    @Override
    @Transactional
    public List<InvestigatorProjection> findAll() {
        List<InvestigatorProjection> investigatorList = repository.findAllBy(InvestigatorProjection.class);
        return investigatorList;
    }
    
    @Override
    @Transactional
    public List<InvestigatorProjection> findAllActive() {
        List<InvestigatorProjection> dtoList = repository.findByWorkingStatus(WorkingStatus.ACTIVE, InvestigatorProjection.class);
        return dtoList;
    }

    @Override
    @Transactional
    public void delete(InvestigatorProjection target) {
        deleteById(target.getId());
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        removeAttorneyAssignments(targetId);
        repository.deleteById(targetId);
    }
    
    
//    Helper methods for severing previously existing investigator assignments 
//      when updating/deleting investigators
    
    private void removeUndesiredAttorneyAssignmentsIfUpdated(InvestigatorForm form) {
        List<Attorney> prevAssignedAttorneys = attorneyRepository
                .findByInvestigator_Id(form.getId(), Attorney.class);
        for(Attorney attorney : prevAssignedAttorneys) {
            if(!form.getAssignedAttorneysIds().contains(attorney.getId())) {
                attorney.setInvestigator(null);
                attorneyRepository.save(attorney);
            }
        }
    }
    
    private void removeAttorneyAssignments(Long investigatorId) {
        List<Attorney> prevAssignedAttorneys = attorneyRepository
                .findByInvestigator_Id(investigatorId, Attorney.class);
        for(Attorney attorney : prevAssignedAttorneys) {
            attorney.setInvestigator(null);
            attorneyRepository.save(attorney);
        }
    }
}
