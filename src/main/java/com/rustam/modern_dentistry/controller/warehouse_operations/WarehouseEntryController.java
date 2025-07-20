package com.rustam.modern_dentistry.controller.warehouse_operations;


import com.rustam.modern_dentistry.dto.request.create.WarehouseEntryCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.WarehouseEntrySearchRequest;
import com.rustam.modern_dentistry.dto.request.update.WarehouseEntryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.WarehouseEntryCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.WarehouseEntryReadResponse;
import com.rustam.modern_dentistry.dto.response.read.WarehouseEntryResponse;
import com.rustam.modern_dentistry.service.warehouse_operations.WarehouseEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/warehouse-entry")
@RequiredArgsConstructor
public class WarehouseEntryController {

    private final WarehouseEntryService warehouseEntryService;

    @PostMapping(path = "/create")
    public ResponseEntity<WarehouseEntryCreateResponse> create(@RequestBody WarehouseEntryCreateRequest warehouseEntryCreateRequest){
        return new ResponseEntity<>(warehouseEntryService.create(warehouseEntryCreateRequest),HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<WarehouseEntryReadResponse>> read(){
        return new ResponseEntity<>(warehouseEntryService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<WarehouseEntryReadResponse>> search(@RequestBody WarehouseEntrySearchRequest warehouseEntrySearchRequest){
        return new ResponseEntity<>(warehouseEntryService.search(warehouseEntrySearchRequest),HttpStatus.OK);
    }

    @GetMapping(path = "/info/{id}")
    public ResponseEntity<WarehouseEntryResponse> info(@PathVariable Long id){
        return new ResponseEntity<>(warehouseEntryService.info(id),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<WarehouseEntryCreateResponse> update(@RequestBody WarehouseEntryUpdateRequest warehouseEntryUpdateRequest){
        return new ResponseEntity<>(warehouseEntryService.update(warehouseEntryUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        warehouseEntryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/delete-entry-product/warehouse-entry/{id}/warehouse-entry-product/{productId}")
    public ResponseEntity<Void> deleteEntryProduct(@PathVariable Long id,@PathVariable Long productId){
        warehouseEntryService.deleteEntryProduct(id,productId);
        return ResponseEntity.ok().build();
    }


}
