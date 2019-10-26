package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pdclientmanager.model.dto.JudgeDto;
import com.pdclientmanager.model.entity.Judge;
import com.pdclientmanager.model.entity.WorkingStatus;
import com.pdclientmanager.repository.JudgeRepository;
import com.pdclientmanager.util.mapper.JudgeMapper;

public class JudgeServiceImpl implements JudgeService {

    private JudgeRepository repository;
    private JudgeMapper mapper;
    
    @Autowired
    public JudgeServiceImpl(JudgeRepository repository, JudgeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Long save(JudgeDto dto) {
        Judge entity = mapper.toJudge(dto);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public JudgeDto findById(Long targetId) {
        Judge entity = repository.findById(targetId)
                .orElseThrow(EntityNotFoundException::new);
        JudgeDto dto = mapper.toJudgeDto(entity);
        return dto;
    }

    @Override
    @Transactional
    public List<JudgeDto> findAll() {
        List<JudgeDto> dtoList = mapper.toJudgeDtoList(repository.findAll());
        return dtoList;
    }

    @Override
    public List<JudgeDto> findAllActive() {
        List<JudgeDto> dtoList = mapper.toJudgeDtoList(
                repository.findByWorkingStatus(WorkingStatus.ACTIVE));
        return dtoList;
    }

    @Override
    public void delete(JudgeDto dto) {
        Judge entity = mapper.toJudge(dto);
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }

}
