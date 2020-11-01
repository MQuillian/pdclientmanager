package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.repository.entity.Charge;

public interface ChargeRepository extends BaseRepository<Charge> {

    <T> Optional<T> findById(Long targetId, Class<T> type);
    
    List<ChargeProjection> findAllBy();
    
    List<ChargeProjection> findFirst10ByNameContainingOrStatuteContaining(String nameQuery, String statuteQuery);
}
