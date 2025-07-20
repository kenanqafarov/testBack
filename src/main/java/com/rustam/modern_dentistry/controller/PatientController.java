package com.rustam.modern_dentistry.controller;

import com.rustam.modern_dentistry.dto.request.create.PatientCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.PatientSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.PatientUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.PatientCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.PatientReadResponse;
import com.rustam.modern_dentistry.dto.response.update.PatientUpdateResponse;
import com.rustam.modern_dentistry.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping(path = "/create")
    public ResponseEntity<PatientCreateResponse> create(@Valid @RequestBody PatientCreateRequest patientCreateRequest) {
        return new ResponseEntity<>(patientService.create(patientCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<PatientReadResponse>> read() {
        return new ResponseEntity<>(patientService.read(), HttpStatus.OK);
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<PatientReadResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.readById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<PatientUpdateResponse> update(@Valid @RequestBody PatientUpdateRequest patientUpdateRequest) {
        return new ResponseEntity<>(patientService.update(patientUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.delete(id), HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<PatientReadResponse>> search(@RequestBody PatientSearchRequest patientSearchRequest){
        return new ResponseEntity<>(patientService.search(patientSearchRequest),HttpStatus.OK);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel(){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=patients.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(patientService.exportReservationsToExcel());
    }
}