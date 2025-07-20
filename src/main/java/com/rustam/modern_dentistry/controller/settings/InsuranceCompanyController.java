package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.InsuranceCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.ICSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.UpdateICRequest;
import com.rustam.modern_dentistry.dto.response.read.InsuranceReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.InsuranceCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/insurance-company")
@RequiredArgsConstructor
public class InsuranceCompanyController {
    private final InsuranceCompanyService insuranceCompanyService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody InsuranceCreateRequest request) {
        insuranceCompanyService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<InsuranceReadResponse>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(insuranceCompanyService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<InsuranceReadResponse>> readList() {
        return ResponseEntity.ok(insuranceCompanyService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<InsuranceReadResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(insuranceCompanyService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateICRequest request) {
        insuranceCompanyService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        insuranceCompanyService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        insuranceCompanyService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<InsuranceReadResponse>> search(ICSearchRequest request, PageCriteria pageCriteria) {
        return ResponseEntity.ok(insuranceCompanyService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Sigorta_sirketleri.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(insuranceCompanyService.exportReservationsToExcel());
    }
}
