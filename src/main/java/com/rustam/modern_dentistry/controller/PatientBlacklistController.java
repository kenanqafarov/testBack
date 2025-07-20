package com.rustam.modern_dentistry.controller;

import com.rustam.modern_dentistry.dto.request.create.PatBlacklistCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.PatBlacklistSearchReq;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.dto.response.read.PatBlacklistReadRes;
import com.rustam.modern_dentistry.service.PatientBlacklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/patient-blacklist")
@RequiredArgsConstructor
public class PatientBlacklistController {
    private final PatientBlacklistService patientBlacklistService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody PatBlacklistCreateReq request) {
        patientBlacklistService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<PatBlacklistReadRes>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(patientBlacklistService.read(pageCriteria));
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<PatBlacklistReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(patientBlacklistService.readById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientBlacklistService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<PatBlacklistReadRes>> search(PatBlacklistSearchReq request, PageCriteria pageCriteria) {
        return ResponseEntity.ok(patientBlacklistService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Pasiyent_qara_siyahi.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(patientBlacklistService.exportReservationsToExcel());
    }
}
