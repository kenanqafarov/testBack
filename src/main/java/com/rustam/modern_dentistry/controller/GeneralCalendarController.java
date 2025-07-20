package com.rustam.modern_dentistry.controller;

import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import com.rustam.modern_dentistry.dto.request.create.NewAppointmentRequest;
import com.rustam.modern_dentistry.dto.request.update.UpdateAppointmentRequest;
import com.rustam.modern_dentistry.dto.response.create.NewAppointmentResponse;
import com.rustam.modern_dentistry.dto.response.read.GeneralCalendarResponse;
import com.rustam.modern_dentistry.dto.response.read.ReadRooms;
import com.rustam.modern_dentistry.dto.response.read.SelectingDoctorViewingPatientResponse;
import com.rustam.modern_dentistry.dto.response.read.SelectingPatientToReadResponse;
import com.rustam.modern_dentistry.service.GeneralCalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/general-calendar")
@RequiredArgsConstructor
public class GeneralCalendarController {

    private final GeneralCalendarService generalCalendarService;

    @GetMapping(path = "/read-doctors")
    public ResponseEntity<List<GeneralCalendarResponse>> readDoctors() {
        return new ResponseEntity<>(generalCalendarService.readDoctors(), HttpStatus.OK);
    }

    @PostMapping(path = "/new-appointment")
    public ResponseEntity<NewAppointmentResponse> newAppointment(@Valid @RequestBody NewAppointmentRequest newAppointmentRequest) {
        return new ResponseEntity<>(generalCalendarService.newAppointment(newAppointmentRequest), HttpStatus.OK);
    }

    @GetMapping(path = "/selecting-doctor-viewing-patient/{doctorId}")
    public ResponseEntity<List<SelectingDoctorViewingPatientResponse>> selectingDoctorViewingPatient(@PathVariable UUID doctorId) {
        return new ResponseEntity<>(generalCalendarService.selectingDoctorViewingPatient(doctorId), HttpStatus.OK);
    }

    @GetMapping(path = "/selecting-room-viewing-patient/{room}")
    public ResponseEntity<List<SelectingDoctorViewingPatientResponse>> selectingRoomViewingPatient(@PathVariable Room room) {
        return new ResponseEntity<>(generalCalendarService.selectingRoomViewingPatient(room), HttpStatus.OK);
    }

    @GetMapping(path = "/selecting-patient-to-read/{patientId}")
    public ResponseEntity<SelectingPatientToReadResponse> selectingPatientToRead(@PathVariable Long patientId) {
        return new ResponseEntity<>(generalCalendarService.selectingPatientToRead(patientId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete-appointment/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        return new ResponseEntity<>(generalCalendarService.delete(id), HttpStatus.OK);
    }

    @PutMapping(path = "/update-appointment")
    public ResponseEntity<NewAppointmentResponse> updateAppointment(@Valid @RequestBody UpdateAppointmentRequest updateAppointmentRequest) {
        return new ResponseEntity<>(generalCalendarService.update(updateAppointmentRequest), HttpStatus.OK);
    }

    @GetMapping(path = "/read-rooms")
    public ResponseEntity<List<ReadRooms>> readRooms() {
        return new ResponseEntity<>(generalCalendarService.read(), HttpStatus.OK);
    }

}
