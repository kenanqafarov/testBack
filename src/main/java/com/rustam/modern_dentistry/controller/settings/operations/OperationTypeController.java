package com.rustam.modern_dentistry.controller.settings.operations;

import com.rustam.modern_dentistry.dto.request.create.OpTypeCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.OperationTypeSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.OpTypeReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.operations.OperationTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/operation-types")
@RequiredArgsConstructor
public class OperationTypeController {
    private final OperationTypeService operationTypeService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody OpTypeCreateRequest request) {
        operationTypeService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<OpTypeReadResponse>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(operationTypeService.read(pageCriteria));
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<OpTypeReadResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(operationTypeService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody OpTypeUpdateRequest request) {
        operationTypeService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        operationTypeService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        operationTypeService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<OpTypeReadResponse>> search(OperationTypeSearchRequest request, PageCriteria criteria) {
        return ResponseEntity.ok(operationTypeService.search(request, criteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Emeliyyat_novleri.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(operationTypeService.exportReservationsToExcel());
    }
}
