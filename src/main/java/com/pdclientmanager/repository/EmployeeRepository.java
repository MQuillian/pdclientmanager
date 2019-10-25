package com.pdclientmanager.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.pdclientmanager.model.entity.Employee;
import com.pdclientmanager.model.entity.EmploymentStatus;

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends BaseRepository<T> {

    List<T> findByEmploymentStatus(EmploymentStatus status);
}
