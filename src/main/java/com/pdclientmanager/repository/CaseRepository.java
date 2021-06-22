package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import com.pdclientmanager.model.projection.CaseProjection;
import com.pdclientmanager.model.projection.OfficeStatsDto;
import com.pdclientmanager.repository.entity.Case;

public interface CaseRepository extends BaseRepository<Case> {
    
    @EntityGraph(value = "Case.fullProjection")
    <T> Optional<T> findById(Long targetId, Class<T> type);
    
    <T> List<T> findByDateClosedIsNull(Class<T> type);
    
    @EntityGraph(value = "Case.fullProjection")
    Page<CaseProjection> findProjectionsWithChargedCountsByClient_Id(Long id, final Pageable pageRequest);
    
    @EntityGraph(value = "Case.fullProjection")
    Page<CaseProjection> findProjectionsWithChargedCountsByAttorney_Id(Long id, final Pageable pageRequest);
    
    <T> List<T> findAllBy(Class<T> type);
    
    <T> Page<T> findAllBy(final Pageable pageRequest, Class<T> type);
    
    <T> List<T> findByAttorney_Id(final Long attorneyid, Class<T> type);
    
    <T> List<T> findByDateClosedIsNullAndAttorney_Id(final Long attorneyId, Class<T> type);
    
    <T> List<T> findFirst10ByDateClosedIsNullAndCaseNumberContaining(final String caseNumber, Class<T> type);
    
    @EntityGraph(value = "Case.fullProjection")
    <T> Page<T> findByClient_NameContaining(final Pageable pageRequest, final String clientName, Class<T> type);
}
