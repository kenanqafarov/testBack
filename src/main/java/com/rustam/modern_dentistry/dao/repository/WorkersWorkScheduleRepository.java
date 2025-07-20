package com.rustam.modern_dentistry.dao.repository;

import com.rustam.modern_dentistry.dao.entity.WorkersWorkSchedule;
import com.rustam.modern_dentistry.dto.response.read.TeethOperationResponse;
import com.rustam.modern_dentistry.dto.response.read.WorkersWorkScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkersWorkScheduleRepository extends JpaRepository<WorkersWorkSchedule,Long>, JpaSpecificationExecutor<WorkersWorkSchedule> {

    @Query("SELECT new com.rustam.modern_dentistry.dto.response.read.WorkersWorkScheduleResponse(w.id, w.weekDay, w.room, b.id, b.name, b.surname, w.startTime, w.finishTime) " +
            "FROM WorkersWorkSchedule w JOIN w.worker b")
    List<WorkersWorkScheduleResponse> findAllWorkersWorkSchedule();
}
