package com.rustam.modern_dentistry.controller.patient_info;

import com.rustam.modern_dentistry.dto.request.create.PatVideosCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatVideosUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatVideosReadRes;
import com.rustam.modern_dentistry.service.patient_info.PatientVideosService;
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
@RequestMapping(path = "/api/v1/patient-videos")
@RequiredArgsConstructor
public class PatientVideosController {
    private final PatientVideosService patientVideosService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart("data") @Valid PatVideosCreateReq request,
                                       @RequestPart("file") MultipartFile file) {
        patientVideosService.create(request, file);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<List<PatVideosReadRes>> read(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientVideosService.read(patientId));
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<PatVideosReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(patientVideosService.readById(id));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart("data") PatVideosUpdateReq request,
                                       @RequestPart("file") MultipartFile file) {
        patientVideosService.update(id, request, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientVideosService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
