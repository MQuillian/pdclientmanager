package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.CaseForm;
import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.CaseRepository;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.Case;
import com.pdclientmanager.util.mapper.CaseMapper;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;

@Service
public class CaseServiceImpl implements CaseService {
    
    private CaseRepository repository;
    private AttorneyRepository attorneyRepository;
    private CaseMapper mapper;
    
    @Autowired
    public CaseServiceImpl(CaseRepository repository, AttorneyRepository attorneyRepository, 
            CaseMapper mapper) {
        this.repository = repository;
        this.attorneyRepository = attorneyRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long save(CaseForm formDto) {
        Case entity = mapper.toCaseFromCaseFormDto(formDto, new CycleAvoidingMappingContext());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <T> T findById(Long targetId, Class<T> type) {
        T courtCase = repository.findById(targetId, type)
                .orElseThrow(EntityNotFoundException::new);
        return courtCase;
    }

    @Override
    @Transactional
    public CaseForm findFormById(Long targetId) {
        Case entity = repository.findById(targetId, Case.class)
                .orElseThrow(EntityNotFoundException::new);
        CaseForm formDto = mapper.toCaseFormDtoFromCase(entity);
        return formDto;
    }

    @Override
    @Transactional
    public <T> List<T> findAll(Class<T> type) {
        List<T> caseList = repository.findAllBy(type);
        return caseList;
    }
    
    @Override
    @Transactional
    public <T> Page<T> findAll(Pageable pageRequest, Class<T> type) {
        Page<T> casePage = repository.findAllBy(pageRequest, type);
        return casePage;
    }

    @Override
    @Transactional
    public <T> List<T> findAllOpen(Class<T> type) {
        List<T> caseList = repository.findByDateClosedIsNull(type);
        return caseList;
    }
    
    @Override
    @Transactional
    public <T> List<T> findAllOpenWithAttorneyId(Long targetId, Class<T> type) {
        List<T> caseList = repository.findByDateClosedIsNullAndAttorney_Id(targetId, type);
        return caseList;
    }
    
    @Override
    @Transactional
    public <T> Page<T> findAllWithClientName(Pageable pageRequest, String clientName, Class<T> type) {
        Page<T> casePage = repository.findByClient_NameContaining(pageRequest, clientName, type);
        return casePage;
    }

    @Override
    @Transactional
    public void delete(CaseProjection target) {
        repository.deleteById(target.getId());
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }
    
    @Override
    @Transactional
    public void reassignOpenCases(Long prevId, Long newId) {
        List<Case> activeCaseload = repository.findByDateClosedIsNullAndAttorney_Id(prevId,
                Case.class);
        reassignAttorneyInEachCase(activeCaseload, prevId, newId);
    }
    
    @Override
    @Transactional
    public void reassignAllCases(Long prevId, Long newId) {
        List<Case> entireCaseload = repository.findByAttorney_Id(prevId, Case.class);
        reassignAttorneyInEachCase(entireCaseload, prevId, newId);
    }
    
    // Helper method for reassigning caseloads
    private void reassignAttorneyInEachCase(List<Case> cases, Long prevId, Long newId) {
        Attorney newAssignedAttorney = attorneyRepository.findById(newId, Attorney.class)
                .orElseThrow(EntityNotFoundException::new);
        
        for(Case courtCase : cases) {
            courtCase.setAttorney(newAssignedAttorney);
        }
    }
}
