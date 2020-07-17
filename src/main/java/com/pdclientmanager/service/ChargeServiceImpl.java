package com.pdclientmanager.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.ChargeForm;
import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.repository.ChargeRepository;
import com.pdclientmanager.repository.entity.Charge;
import com.pdclientmanager.util.mapper.ChargeMapper;

@Service
public class ChargeServiceImpl implements ChargeService {

    private ChargeRepository repository;
    private ChargeMapper mapper;
    
    @Autowired
    public ChargeServiceImpl(ChargeRepository repository, ChargeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Long save(ChargeForm form) {
        Charge entity = mapper.toCharge(form);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <T> T findById(Long targetId, Class<T> type) {
        T charge = repository.findById(targetId, type)
                .orElseThrow(EntityNotFoundException::new);
        return charge;
    }
    
    @Override
    @Transactional
    public ChargeForm findFormById(Long targetId) {
        Charge entity = repository.findById(targetId, Charge.class)
                .orElseThrow(EntityNotFoundException::new);
        ChargeForm form = mapper.toChargeForm(entity);
        
        return form;
    }
    
    @Override
    @Transactional
    public List<ChargeProjection> findByNameOrStatute(String query) {
        List<ChargeProjection> chargeList = repository.
                findFirst10ByNameContainingOrStatuteContaining(query, query);
        return chargeList;
    }

    @Override
    @Transactional
    public List<ChargeProjection> findAll() {
        List<ChargeProjection> chargeList = repository.findAllBy();
        return chargeList;
    }

    @Override
    @Transactional
    public void delete(ChargeProjection target) {
        deleteById(target.getId());
    }

    @Override
    @Transactional
    public void deleteById(Long targetId) {
        repository.deleteById(targetId);
    }

}
