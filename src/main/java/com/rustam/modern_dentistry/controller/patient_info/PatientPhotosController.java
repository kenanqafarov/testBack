package com.rustam.modern_dentistry.controller.patient_info;

import com.rustam.modern_dentistry.dto.request.create.PatPhotosCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatPhotosUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatPhotosReadRes;
import com.rustam.modern_dentistry.service.patient_info.PatientPhotosService;
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
@RequestMapping(path = "/api/v1/patient-photos")
@RequiredArgsConstructor
public class PatientPhotosController {
    private final PatientPhotosService patientPhotosService;

        @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Void> create(@RequestPart("data") @Valid PatPhotosCreateReq request,
                                           @RequestPart("file") MultipartFile file) {
            patientPhotosService.create(request, file);
            return ResponseEntity.status(CREATED).build();
        }

    @GetMapping("/read")
    public ResponseEntity<List<PatPhotosReadRes>> read(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientPhotosService.read(patientId));
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<PatPhotosReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(patientPhotosService.readById(id));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart("data") PatPhotosUpdateReq request,
                                       @RequestPart("file") MultipartFile file) {
        patientPhotosService.update(id, request, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientPhotosService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
