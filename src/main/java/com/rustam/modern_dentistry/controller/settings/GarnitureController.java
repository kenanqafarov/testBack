package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.GarnitureCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateGarnitureReq;
import com.rustam.modern_dentistry.dto.response.read.GarnitureReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.GarnitureService;
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
@RequestMapping("/api/v1/garnitures")
@RequiredArgsConstructor
public class GarnitureController {
    private final GarnitureService garnitureService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody GarnitureCreateReq request) {
        garnitureService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<GarnitureReadRes>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(garnitureService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<GarnitureReadRes>> readList() {
        return ResponseEntity.ok(garnitureService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<GarnitureReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(garnitureService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateGarnitureReq request) {
        garnitureService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        garnitureService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        garnitureService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<GarnitureReadRes>> search(SearchByNameAndStatus request,
                                                                 PageCriteria pageCriteria) {
        return ResponseEntity.ok(garnitureService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Garniturlar.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(garnitureService.exportToExcel());
    }

}
