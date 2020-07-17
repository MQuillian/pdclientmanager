package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

import com.pdclientmanager.model.projection.AttorneyProjection;
import com.pdclientmanager.repository.entity.Attorney;
import com.pdclientmanager.repository.entity.WorkingStatus;

public interface AttorneyRepository extends BaseRepository<Attorney> {
    
    @EntityGraph(value = "Attorney.fullProjection")
    <T> Optional<T> findById(Long targetId, Class<T> type);
    
    <T> List<T> findByInvestigator_Id(Long targetId, Class<T> type);
    
    @EntityGraph(value = "Attorney.fullProjection")
    List<AttorneyProjection> findAllProjectedBy();
    
    <T> List<T> findByWorkingStatus(WorkingStatus workingStatus, Class<T> type);
    
    <T> List<T> findBy(Class<T> type);
}