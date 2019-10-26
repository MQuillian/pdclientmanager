package com.pdclientmanager.repository;

import java.util.List;

import com.pdclientmanager.model.entity.Judge;
import com.pdclientmanager.model.entity.WorkingStatus;

public interface JudgeRepository extends BaseRepository<Judge> {

    List<Judge> findByWorkingStatus(WorkingStatus workingStatus);
}
