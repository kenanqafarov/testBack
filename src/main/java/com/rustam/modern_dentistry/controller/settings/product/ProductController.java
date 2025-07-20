package com.rustam.modern_dentistry.controller.settings.product;

import com.rustam.modern_dentistry.dto.request.create.ProductRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductUpdatedStatusRequest;
import com.rustam.modern_dentistry.dto.response.create.ProductResponse;
import com.rustam.modern_dentistry.dto.response.read.ProductReadResponse;
import com.rustam.modern_dentistry.service.settings.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/create")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.create(productRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ProductResponse>> read() {
        return new ResponseEntity<>(productService.read(), HttpStatus.OK);
    }

    @GetMapping(path = "/read-by-id/{id}")
    public ResponseEntity<ProductResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.readById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<ProductReadResponse>> search(@RequestBody ProductSearchRequest productSearchRequest) {
        return new ResponseEntity<>(productService.search(productSearchRequest), HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ProductResponse> update(@RequestBody ProductUpdateRequest productUpdateRequest) {
        return new ResponseEntity<>(productService.update(productUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/status-updated")
    public ResponseEntity<ProductReadResponse> statusUpdated(@RequestBody ProductUpdatedStatusRequest productUpdatedStatusRequest) {
        return new ResponseEntity<>(productService.statusUpdated(productUpdatedStatusRequest), HttpStatus.OK);
    }
}
