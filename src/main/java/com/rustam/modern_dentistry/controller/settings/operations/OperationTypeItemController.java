package com.rustam.modern_dentistry.controller.settings.operations;

import com.rustam.modern_dentistry.dto.request.update.OpTypeItemUpdateRequest;
import com.rustam.modern_dentistry.dto.request.read.OpTypeItemSearchRequest;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadByIdResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadResponse;
import com.rustam.modern_dentistry.dto.request.create.OpTypeItemCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.operations.OperationTypeItemService;
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
@RequestMapping("/api/v1/operation-type-items")
@RequiredArgsConstructor
public class OperationTypeItemController {
    private final OperationTypeItemService operationTypeItemService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody OpTypeItemCreateRequest request) {
        operationTypeItemService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<PageResponse<OpTypeItemReadResponse>> read(@PathVariable Long id,
                                                                     PageCriteria pageCriteria) {
        return ResponseEntity.ok(operationTypeItemService.read(id, pageCriteria));
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<OpTypeItemReadByIdResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(operationTypeItemService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody OpTypeItemUpdateRequest request) {
        operationTypeItemService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        operationTypeItemService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        operationTypeItemService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<OpTypeItemReadResponse>> search(OpTypeItemSearchRequest request, PageCriteria criteria) {
        return ResponseEntity.ok(operationTypeItemService.search(request, criteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Emeliyyat_siyahisi.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(operationTypeItemService.exportReservationsToExcel());
    }
}
