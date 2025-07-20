package com.rustam.modern_dentistry.controller.warehouse_operations;

import com.rustam.modern_dentistry.dto.request.read.WarehouseSearchRequest;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReadResponse;
import com.rustam.modern_dentistry.service.warehouse_operations.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping(path = "/read-warehouse")
    public ResponseEntity<List<WarehouseReadResponse>> read(){
        return new ResponseEntity<>(warehouseService.read(), HttpStatus.OK);
    }

    @PostMapping(path = "/search-warehouse")
    public ResponseEntity<List<WarehouseReadResponse>> search(@RequestBody WarehouseSearchRequest warehouseSearchRequest){
        return new ResponseEntity<>(warehouseService.search(warehouseSearchRequest),HttpStatus.OK);
    }
}
