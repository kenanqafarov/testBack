package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.CeramicCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateCeramicReq;
import com.rustam.modern_dentistry.dto.response.read.CeramicReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.CeramicService;
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
@RequestMapping("/api/v1/metal")
@RequiredArgsConstructor
public class CeramicController {
    private final CeramicService ceramicService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody CeramicCreateReq request) {
        ceramicService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<CeramicReadRes>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(ceramicService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<CeramicReadRes>> readList() {
        return ResponseEntity.ok(ceramicService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<CeramicReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(ceramicService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateCeramicReq request) {
        ceramicService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        ceramicService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ceramicService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<CeramicReadRes>> search(SearchByNameAndStatus request,
                                                               PageCriteria pageCriteria) {
        return ResponseEntity.ok(ceramicService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Keramikalar.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(ceramicService.exportToExcel());
    }
}
