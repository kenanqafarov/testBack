package com.rustam.modern_dentistry.controller.patient_info;

import com.rustam.modern_dentistry.dto.request.create.PatAnamnesisCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatAnamnesisUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatAnamnesisReadRes;
import com.rustam.modern_dentistry.dto.response.update.ReservationUpdateResponse;
import com.rustam.modern_dentistry.service.patient_info.PatientAnamnesisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/patient-anamnesis")
@RequiredArgsConstructor
public class PatientAnamnesisController {
    private final PatientAnamnesisService patientAnamnesisService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody PatAnamnesisCreateReq req) {
        patientAnamnesisService.create(req);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<List<PatAnamnesisReadRes>> read() {
        return ResponseEntity.ok(patientAnamnesisService.read());
    }

    @GetMapping("/read/{patientId}")
    public ResponseEntity<List<PatAnamnesisReadRes>> readAllById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientAnamnesisService.readAllById(patientId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                                            @Valid @RequestBody PatAnamnesisUpdateReq request) {
        patientAnamnesisService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientAnamnesisService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
