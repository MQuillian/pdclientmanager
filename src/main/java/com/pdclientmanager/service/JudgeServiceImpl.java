package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.JudgeForm;
import com.pdclientmanager.model.projection.JudgeProjection;
import com.pdclientmanager.repository.JudgeRepository;
import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.repository.entity.WorkingStatus;
import com.pdclientmanager.util.mapper.JudgeMapper;

@Service
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
    public Long save(JudgeForm form) {
        Judge entity = mapper.toJudge(form);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <T> T findById(Long targetId, Class<T> type) {
        T judge = repository.findById(targetId, type)
                .orElseThrow(EntityNotFoundException::new);
        return judge;
    }
    
    @Override
    @Transactional
    public JudgeForm findFormById(Long targetId) {
        Judge entity = repository.findById(targetId, Judge.class)
                .orElseThrow(EntityNotFoundException::new);
        JudgeForm judge = mapper.toJudgeForm(entity);
        return judge;
    }

    @Override
    @Transactional
    public List<JudgeProjection> findAll() {
        List<JudgeProjection> judgeList = repository.findAllBy(JudgeProjection.class);
        return judgeList;
    }

    @Override
    @Transactional
    public List<JudgeProjection> findAllActive() {
        List<JudgeProjection> judgeList = repository.findByWorkingStatus(WorkingStatus.ACTIVE, JudgeProjection.class);
        return judgeList;
    }

    @Override
    @Transactional
    public void delete(JudgeProjection judge) {
        repository.deleteById(judge.getId());
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }

}
