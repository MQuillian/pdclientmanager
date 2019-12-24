package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.dto.InvestigatorDto;
import com.pdclientmanager.model.dto.InvestigatorFormDto;
import com.pdclientmanager.model.entity.Attorney;
import com.pdclientmanager.model.entity.Investigator;
import com.pdclientmanager.model.entity.WorkingStatus;
import com.pdclientmanager.repository.AttorneyRepository;
import com.pdclientmanager.repository.InvestigatorRepository;
import com.pdclientmanager.util.mapper.CycleAvoidingMappingContext;
import com.pdclientmanager.util.mapper.InvestigatorMapper;

@Service
public class InvestigatorServiceImpl implements InvestigatorService {
    
    private InvestigatorRepository investigatorRepository;
    private AttorneyRepository attorneyRepository;
    private InvestigatorMapper mapper;
    
    @Autowired
    public InvestigatorServiceImpl(InvestigatorRepository investigatorRepository,
            AttorneyRepository attorneyRepository, InvestigatorMapper mapper) {
        this.investigatorRepository = investigatorRepository;
        this.attorneyRepository = attorneyRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Long save(InvestigatorFormDto formDto) {
        if(!formDto.isNew()) {
            removeUndesiredAttorneyAssignmentsIfUpdated(formDto);
        }
        Investigator entity = mapper.toInvestigatorFromInvestigatorFormDto(formDto, new CycleAvoidingMappingContext());
        investigatorRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public InvestigatorDto findById(Long targetId) {
        Investigator entity = investigatorRepository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        InvestigatorDto dto = mapper.toInvestigatorDto(entity);
        return dto;
    }
    
    @Override
    @Transactional
    public InvestigatorFormDto findFormById(Long targetId) {
        Investigator entity = investigatorRepository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        InvestigatorFormDto formDto = mapper.toInvestigatorFormDtoFromInvestigator(entity);
        return formDto;
    }

    @Override
    @Transactional
    public List<InvestigatorDto> findAll() {
        List<InvestigatorDto> dtoList = mapper.toInvestigatorDtoList(investigatorRepository.findAll());
        return dtoList;
    }
    
    @Override
    @Transactional
    public List<InvestigatorDto> findAllActive() {
        List<InvestigatorDto> dtoList = mapper
                .toInvestigatorDtoList(investigatorRepository.findByWorkingStatus(WorkingStatus.ACTIVE));
        return dtoList;
    }

    @Override
    @Transactional
    public void delete(InvestigatorDto dto) {
        Investigator entity = mapper.toInvestigator(dto, new CycleAvoidingMappingContext());
        removeAttorneyAssignments(dto.getId());
        investigatorRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        removeAttorneyAssignments(targetId);
        investigatorRepository.deleteById(targetId);
    }
    
    
//    Helper methods for severing previously existing investigator assignments 
//      when updating/deleting investigators
    
    private void removeUndesiredAttorneyAssignmentsIfUpdated(InvestigatorFormDto formDto) {
        List<Attorney> prevAssignedAttorneys = attorneyRepository
                .findByInvestigator_Id(formDto.getId());
        for(Attorney attorney : prevAssignedAttorneys) {
            if(!formDto.getAssignedAttorneysIds().contains(attorney.getId())) {
                attorney.setInvestigator(null);
            }
        }
    }
    
    private void removeAttorneyAssignments(Long investigatorId) {
        List<Attorney> prevAssignedAttorneys = attorneyRepository
                .findByInvestigator_Id(investigatorId);
        for(Attorney attorney : prevAssignedAttorneys) {
            attorney.setInvestigator(null);
        }
    }
}
