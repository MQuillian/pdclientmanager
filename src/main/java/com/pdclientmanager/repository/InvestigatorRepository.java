package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

import com.pdclientmanager.repository.entity.Investigator;
import com.pdclientmanager.repository.entity.WorkingStatus;

public interface InvestigatorRepository extends BaseRepository<Investigator> {

    @EntityGraph(value = "Investigator.fullProjection")
    <T> Optional<T> findById(Long targetId, Class<T> type);
    
    <T> List<T> findByWorkingStatus(WorkingStatus workingStatus, Class<T> type);
    
    <T> List<T> findAllBy(Class<T> type);
}
