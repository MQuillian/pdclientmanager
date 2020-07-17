package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import com.pdclientmanager.repository.entity.Judge;
import com.pdclientmanager.repository.entity.WorkingStatus;

public interface JudgeRepository extends BaseRepository<Judge> {

    <T> Optional<T> findById(Long targetId, Class<T> type);
    
    <T> List<T> findAllBy(Class<T> type);
    
    <T> List<T> findByWorkingStatus(WorkingStatus workingStatus, Class<T> type);
}
