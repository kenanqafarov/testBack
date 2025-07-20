package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.ColorCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateColorReq;
import com.rustam.modern_dentistry.dto.response.read.ColorReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.ColorService;
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
@RequestMapping("/api/v1/colorss")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody ColorCreateReq request) {
        colorService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<ColorReadRes>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(colorService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<ColorReadRes>> readList() {
        return ResponseEntity.ok(colorService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<ColorReadRes> readById(@PathVariable Long id) {
        return ResponseEntity.ok(colorService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateColorReq request) {
        colorService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        colorService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colorService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ColorReadRes>> search(SearchByNameAndStatus request,
                                                             PageCriteria pageCriteria) {
        return ResponseEntity.ok(colorService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Colors.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(colorService.exportToExcel());
    }
}
