package com.rustam.modern_dentistry.controller.settings.anamnesis;

import com.rustam.modern_dentistry.dto.request.create.AnemnesisCatCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.AnemnesisCatSearchReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateAnemnesisCatReq;
import com.rustam.modern_dentistry.dto.response.read.AnamnesisCatReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.anemnesis.AnemnesisCategoryService;
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
@RequestMapping(path = "/api/v1/anamnesis-categories")
@RequiredArgsConstructor
public class AnamnesisCategoryController {
    private final AnemnesisCategoryService anemnesisCategoryService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody AnemnesisCatCreateReq request) {
        anemnesisCategoryService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<AnamnesisCatReadResponse>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(anemnesisCategoryService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<AnamnesisCatReadResponse>> readList() {
        return ResponseEntity.ok(anemnesisCategoryService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<AnamnesisCatReadResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(anemnesisCategoryService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateAnemnesisCatReq request) {
        anemnesisCategoryService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        anemnesisCategoryService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        anemnesisCategoryService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<AnamnesisCatReadResponse>> search(AnemnesisCatSearchReq request,
                                                                         PageCriteria pageCriteria) {
        return ResponseEntity.ok(anemnesisCategoryService.search(request, pageCriteria));
    }

        @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Anamnez_kateqoriyalari.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(anemnesisCategoryService.exportReservationsToExcel());
    }
}
