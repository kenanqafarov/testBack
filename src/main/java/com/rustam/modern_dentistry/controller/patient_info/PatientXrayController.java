package com.rustam.modern_dentistry.controller.patient_info;

import com.rustam.modern_dentistry.dto.request.create.PatXrayCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatXrayUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatXrayReadRes;
import com.rustam.modern_dentistry.service.patient_info.PatientXrayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/patient-xray")
@RequiredArgsConstructor
public class PatientXrayController {
    private final PatientXrayService patientXrayService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart("data") @Valid PatXrayCreateReq request,
                                       @RequestPart("file") MultipartFile file) {
        patientXrayService.create(request, file);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<List<PatXrayReadRes>> read(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientXrayService.read(patientId));
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<PatXrayReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(patientXrayService.readById(id));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart("data") PatXrayUpdateReq request,
                                       @RequestPart("file") MultipartFile file) {
        patientXrayService.update(id, request, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientXrayService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
