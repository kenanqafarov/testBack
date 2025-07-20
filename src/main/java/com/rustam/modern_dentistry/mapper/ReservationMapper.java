package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.Reservation;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.request.create.ReservationCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.ReservationUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.ReservationCreateResponse;
import com.rustam.modern_dentistry.dto.response.excel.ReservationExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.ReservationReadResponse;
import com.rustam.modern_dentistry.dto.response.update.ReservationUpdateResponse;
import org.springframework.stereotype.Component;

import static com.rustam.modern_dentistry.dao.entity.enums.status.ReservationStatus.ACTIVE;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationCreateRequest request, Doctor doctor, Patient patient) {

        return Reservation.builder()
                .status(ACTIVE)
                .doctor(doctor)
                .patient(patient)
                .weekDays(request.getWeekDays())
                .endTime(request.getEndTime())
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .startTime(request.getStartTime())
                .build();
    }

    public ReservationCreateResponse toCreateDto(Reservation reservation) {

        return ReservationCreateResponse.builder()
                .id(reservation.getId())
                .endDate(reservation.getEndDate())
                .startDate(reservation.getStartDate())
                .endTime(reservation.getEndTime())
                .startTime(reservation.getStartTime())
                .weekDays(reservation.getWeekDays())
                .patientId(reservation.getPatient().getId())
                .doctorId(reservation.getDoctor().getId())
                .status(reservation.getStatus())
                .build();
    }

    public ReservationReadResponse toReadDto(Reservation reservation) {

        return ReservationReadResponse.builder()
                .id(reservation.getId())
                .mobilePhone(reservation.getPatient().getPhone())
                .endDate(reservation.getEndDate())
                .startDate(reservation.getStartDate())
                .endTime(reservation.getEndTime())
                .startTime(reservation.getStartTime())
                .doctor(reservation.getDoctor().getName() + " " + reservation.getDoctor().getSurname())
                .patient(reservation.getPatient().getName() + " " + reservation.getPatient().getSurname())
                .status(reservation.getStatus())
                .build();
    }

    public ReservationUpdateResponse toUpdateDto(Reservation reservation) {

        return ReservationUpdateResponse.builder()
                .endDate(reservation.getEndDate())
                .startDate(reservation.getStartDate())
                .endTime(reservation.getEndTime())
                .startTime(reservation.getStartTime())
                .patientId(reservation.getPatient().getId())
                .doctorId(reservation.getDoctor().getId())
                .build();
    }

    public ReservationExcelResponse toExcelDto(Reservation reservation) {

        return ReservationExcelResponse.builder()
                .mobilePhone(reservation.getPatient().getPhone())
                .endDate(reservation.getEndDate())
                .startDate(reservation.getStartDate())
                .endTime(reservation.getEndTime())
                .startTime(reservation.getStartTime())
                .doctor(reservation.getDoctor().getName() + " " + reservation.getDoctor().getSurname())
                .patient(reservation.getPatient().getName() + " " + reservation.getPatient().getSurname())
                .status(reservation.getStatus())
                .build();
    }

    public Reservation updateReservation(Reservation reservation, ReservationUpdateRequest request, Doctor doctor, Patient patient) {

        if (request == null) return reservation;

        reservation.setStartDate(request.getStartDate() != null ? request.getStartDate() : reservation.getStartDate());
        reservation.setEndDate(request.getEndDate() != null ? request.getEndDate() : reservation.getEndDate());
        reservation.setStartTime(request.getStartTime() != null ? request.getStartTime() : reservation.getStartTime());
        reservation.setEndTime(request.getEndTime() != null ? request.getEndTime() : reservation.getEndTime());
        reservation.setWeekDays(request.getWeekDays() != null ? request.getWeekDays() : reservation.getWeekDays());
        reservation.setDoctor(request.getDoctorId() != null ? doctor : reservation.getDoctor());
        reservation.setPatient(request.getPatientId() != null ? patient : reservation.getPatient());

        return reservation;
    }
}
