package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.PriceCategoryCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.PriceCategorySearchRequest;
import com.rustam.modern_dentistry.dto.request.update.PriceCategoryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.PriceCategoryReadResponse;
import com.rustam.modern_dentistry.service.settings.PriceCategoryService;
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
@RequestMapping(path = "/api/v1/price-categories")
@RequiredArgsConstructor
public class PriceCategoryController {
    private final PriceCategoryService priceCategoryService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody PriceCategoryCreateRequest request) {
        priceCategoryService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<List<PriceCategoryReadResponse>> read() {
        return ResponseEntity.ok(priceCategoryService.read());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<PriceCategoryReadResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(priceCategoryService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PriceCategoryUpdateRequest request) {
        priceCategoryService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        priceCategoryService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        priceCategoryService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PriceCategoryReadResponse>> search(PriceCategorySearchRequest request) {
        return ResponseEntity.ok(priceCategoryService.search(request));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qiymet_kateqoriyalari.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(priceCategoryService.exportReservationsToExcel());
    }
}
