package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.MetalCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateMetalReq;
import com.rustam.modern_dentistry.dto.response.read.MetalReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.MetalService;
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
@RequestMapping("/api/v1/metalss")
@RequiredArgsConstructor
public class MetalController {
    private final MetalService metalService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody MetalCreateReq request) {
        metalService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<MetalReadRes>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(metalService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<MetalReadRes>> readList() {
        return ResponseEntity.ok(metalService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<MetalReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(metalService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateMetalReq request) {
        metalService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        metalService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        metalService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<MetalReadRes>> search(SearchByNameAndStatus request,
                                                             PageCriteria pageCriteria) {
        return ResponseEntity.ok(metalService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Metallar.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(metalService.exportToExcel());
    }
}
