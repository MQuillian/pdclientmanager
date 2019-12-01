package com.pdclientmanager.repository;

import java.util.List;

import com.pdclientmanager.model.entity.Charge;

public interface ChargeRepository extends BaseRepository<Charge> {

    List<Charge> findFirst10ByNameContainingOrStatuteContaining(String nameQuery, String statuteQuery);
    
}
