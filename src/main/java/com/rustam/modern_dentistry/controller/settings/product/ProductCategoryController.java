package com.rustam.modern_dentistry.controller.settings.product;

import com.rustam.modern_dentistry.dto.request.create.ProductCategoryRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductCategorySearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductCategoryStatusUpdatedRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductCategoryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.ProductCategoryResponse;
import com.rustam.modern_dentistry.dto.response.read.ProductCategoryReadResponse;
import com.rustam.modern_dentistry.service.settings.product.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/product-category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        return new ResponseEntity<>(productCategoryService.create(productCategoryRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ProductCategoryReadResponse>> read() {
        return new ResponseEntity<>(productCategoryService.read(), HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<ProductCategoryResponse>> search(@RequestBody ProductCategorySearchRequest productCategorySearchRequest){
        return new ResponseEntity<>(productCategoryService.search(productCategorySearchRequest),HttpStatus.OK);
    }

    @GetMapping(path = "/read-by-id/{id}")
    public ResponseEntity<ProductCategoryReadResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(productCategoryService.readById(id),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ProductCategoryResponse> update(@RequestBody ProductCategoryUpdateRequest productCategoryUpdateRequest){
        return new ResponseEntity<>(productCategoryService.update(productCategoryUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/status-updated")
    public ResponseEntity<ProductCategoryResponse> statusUpdated(@RequestBody ProductCategoryStatusUpdatedRequest productCategoryStatusUpdatedRequest){
        return new ResponseEntity<>(productCategoryService.statusUpdated(productCategoryStatusUpdatedRequest),HttpStatus.OK);
    }
}
