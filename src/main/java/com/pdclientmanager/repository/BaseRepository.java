package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T> extends PagingAndSortingRepository<T, Long> {

    // Wrapper for JpaRepository - selectively exposes only the below methods
    // Spring will create proxy instances for injected interfaces of this type
    
    Page<T> findAll(Pageable pageable);
    
    <S extends T> S save(S entity);
    
    Optional<T> findById(Long id);
    
    boolean existsById(Long id);
    
    long count();
    
    void deleteById(Long id);
    
    void delete(T entity);
    
    void deleteAll(Iterable<? extends T> entities);
    
    void deleteAll();
    
    List<T> findAll();
    
    List<T> findAll(Sort sort);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    T getOne(Long id);
}