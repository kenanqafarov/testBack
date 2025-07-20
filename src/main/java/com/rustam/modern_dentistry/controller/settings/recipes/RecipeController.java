package com.rustam.modern_dentistry.controller.settings.recipes;

import com.rustam.modern_dentistry.dto.request.create.RecipeCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.RecipeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.RecipeReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.service.settings.recipes.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody RecipeCreateRequest request) {
        recipeService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/read")
    public ResponseEntity<PageResponse<RecipeReadResponse>> read(PageCriteria pageCriteria) {
        return ResponseEntity.ok(recipeService.read(pageCriteria));
    }

    @GetMapping("/read-list")
    public ResponseEntity<List<RecipeReadResponse>> read() {
        return ResponseEntity.ok(recipeService.readList());
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RecipeReadResponse> readById(@RequestParam Long id) {
        return ResponseEntity.ok(recipeService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody RecipeUpdateRequest request) {
        recipeService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        recipeService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<RecipeReadResponse>> search(PageCriteria pageCriteria,
                                                                   SearchByNameAndStatus request) {
        return ResponseEntity.ok(recipeService.search(pageCriteria, request));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reseptler.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(recipeService.exportReservationsToExcel());
    }
}
