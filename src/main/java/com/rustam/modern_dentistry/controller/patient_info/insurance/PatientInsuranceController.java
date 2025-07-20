package com.rustam.modern_dentistry.controller.patient_info.insurance;

import com.rustam.modern_dentistry.dto.request.create.PatientInsuranceCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.PatInsuranceUpdateReq;
import com.rustam.modern_dentistry.dto.request.update.PatInsuranceUpdateStatusReq;
import com.rustam.modern_dentistry.dto.response.read.PatientInsuranceReadResponse;
import com.rustam.modern_dentistry.service.patient_info.insurance.PatientInsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/patient-insurance")
@RequiredArgsConstructor
public class PatientInsuranceController {
    private final PatientInsuranceService patientInsuranceService;

    @PostMapping("/create")
    ResponseEntity<Void> create(@Valid @RequestBody PatientInsuranceCreateRequest request) {
        patientInsuranceService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    ResponseEntity<List<PatientInsuranceReadResponse>> readAllById(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientInsuranceService.readAllById(patientId));
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Void> update(@PathVariable Long id,
                                @RequestBody PatInsuranceUpdateReq request) {
        patientInsuranceService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-status/{id}")
    ResponseEntity<List<PatientInsuranceReadResponse>> updateStatus(@PathVariable Long id,
                                                                    @RequestBody PatInsuranceUpdateStatusReq request) {
        return ResponseEntity.ok(patientInsuranceService.updateStatus(id, request));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        patientInsuranceService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
