package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.UpdateBlacklistResultReq;
import com.rustam.modern_dentistry.dto.request.create.BlacklistResultCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.BlacklistResultSearchReq;
import com.rustam.modern_dentistry.dto.response.read.BlacklistResultReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.BlacklistResultService;
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
@RequestMapping("/api/v1/blacklist-results")
@RequiredArgsConstructor
public class BlackListResult {
    private final BlacklistResultService blacklistResultService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody BlacklistResultCreateReq request) {
        blacklistResultService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<BlacklistResultReadRes>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(blacklistResultService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<BlacklistResultReadRes>> readList() {
        return ResponseEntity.ok(blacklistResultService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<BlacklistResultReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(blacklistResultService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateBlacklistResultReq request) {
        blacklistResultService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        blacklistResultService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blacklistResultService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BlacklistResultReadRes>> search(BlacklistResultSearchReq request,
                                                                       PageCriteria pageCriteria) {
        return ResponseEntity.ok(blacklistResultService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Qara_siyahi_sebebi.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(blacklistResultService.exportReservationsToExcel());
    }
}
