package com.pdclientmanager.util.mapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdclientmanager.repository.JudgeRepository;
import com.pdclientmanager.repository.entity.Judge;

@Component
public class JudgeResolver {

    private JudgeRepository repository;
    
    @Autowired
    public JudgeResolver(JudgeRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    public Judge resolve(Long judgeId, @TargetType Class<Judge> type) {
        if(judgeId != null) {
            return repository.findById(judgeId).orElseThrow(EntityNotFoundException::new);
        } else {
            return new Judge();
        }
    }
    
    public Long toLong(Judge entity) {
        return entity != null ? entity.getId() : null;
    }
}
