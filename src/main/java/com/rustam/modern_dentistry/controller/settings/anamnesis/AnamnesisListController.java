package com.rustam.modern_dentistry.controller.settings.anamnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisList;
import com.rustam.modern_dentistry.dto.request.create.AnemnesisListCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.AnemnesisListSearchReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateAnemnesisListReq;
import com.rustam.modern_dentistry.dto.response.read.AnemnesisListReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.anemnesis.AnemnesisListService;
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
@RequestMapping(path = "/api/v1/anamnesis-list")
@RequiredArgsConstructor
public class AnamnesisListController {
    private final AnemnesisListService anemnesisListService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody AnemnesisListCreateReq request) {
        anemnesisListService.create(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<AnemnesisListReadResponse>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(anemnesisListService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<AnemnesisListReadResponse>> readList() {
        return ResponseEntity.ok(anemnesisListService.readList());
    }

    @GetMapping("/read-by-id/{id}")
    public ResponseEntity<AnemnesisListReadResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(anemnesisListService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateAnemnesisListReq request) {
        anemnesisListService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        anemnesisListService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        anemnesisListService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<AnamnesisList>> search(AnemnesisListSearchReq request,
                                                              PageCriteria pageCriteria) {
        return ResponseEntity.ok(anemnesisListService.search(request, pageCriteria));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Anamnez_list.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(anemnesisListService.exportReservationsToExcel());
    }
}
