package com.rustam.modern_dentistry.dao.repository;


import com.rustam.modern_dentistry.dao.entity.GeneralCalendar;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import com.rustam.modern_dentistry.dto.response.read.SelectingDoctorViewingPatientResponse;
import com.rustam.modern_dentistry.dto.response.read.SelectingPatientToReadResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeneralCalendarRepository extends JpaRepository<GeneralCalendar,Long> {
    @Query("""
    SELECT new com.rustam.modern_dentistry.dto.response.read.SelectingDoctorViewingPatientResponse(
        g.patient.name, g.appointment, g.date, g.time, g.period, g.room
    )
    FROM GeneralCalendar g
    JOIN g.patient p
    WHERE g.doctor.id = :doctorId
""")
    List<SelectingDoctorViewingPatientResponse> findAllByDoctorId(UUID doctorId);

    @Query("SELECT new com.rustam.modern_dentistry.dto.response.read.SelectingPatientToReadResponse(" +
            "d.name, p.name, g.time, g.date ,g.room, g.appointment) " +
            "FROM GeneralCalendar g " +
            "JOIN g.doctor d " +
            "JOIN g.patient p " +
            "WHERE g.patient.id = :patientId AND g.appointment IN ('MEETING', 'CAME')")
    Optional<SelectingPatientToReadResponse> findByPatientId(Long patientId);

    @Query("""
    SELECT CASE 
        WHEN COUNT(g) > 0 AND MAX(g.appointment) = 'MEETING' THEN FALSE
        WHEN COUNT(g) > 0 AND MAX(g.appointment) IN ('CAME', 'CANCELED') THEN TRUE
        ELSE TRUE
    END 
    FROM GeneralCalendar g 
    WHERE g.patient.id = :id
""")
    boolean existsActivePatientById(Long id);

    @Query("""
    SELECT new com.rustam.modern_dentistry.dto.response.read.SelectingDoctorViewingPatientResponse(
        g.patient.name, g.appointment, g.date, g.time, g.period, g.room
    )
    FROM GeneralCalendar g
    JOIN g.patient p
    WHERE g.room = :room
""")
    List<SelectingDoctorViewingPatientResponse> findAllRoom(Room room);

    @Transactional
    @Modifying
    @Query("DELETE FROM GeneralCalendar g WHERE g.patient.id = :patientId AND g.time = :time")
    GeneralCalendar deleteByPatientIdAndTime(Long patientId,LocalTime time);
}
