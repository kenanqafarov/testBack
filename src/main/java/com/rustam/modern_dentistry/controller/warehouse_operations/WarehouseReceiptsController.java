package com.rustam.modern_dentistry.controller.warehouse_operations;

import com.rustam.modern_dentistry.dto.request.read.WarehouseReceiptsRequest;
import com.rustam.modern_dentistry.dto.request.update.WarehouseReceiptsStatusUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReceiptsInfoResponse;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReceiptsResponse;
import com.rustam.modern_dentistry.service.warehouse_operations.WarehouseReceiptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/warehouse-receipts")
@RequiredArgsConstructor
public class WarehouseReceiptsController {

    private final WarehouseReceiptsService warehouseReceiptsService;

    @PostMapping(path = "/search")
    public ResponseEntity<List<WarehouseReceiptsResponse>> search(@RequestBody WarehouseReceiptsRequest warehouseReceiptsRequest){
        return new ResponseEntity<>(warehouseReceiptsService.search(warehouseReceiptsRequest), HttpStatus.OK);
    }

    @PutMapping(path = "/pending-status-updated")
    public ResponseEntity<WarehouseReceiptsResponse> update(@RequestBody WarehouseReceiptsStatusUpdateRequest warehouseReceiptsStatusUpdateRequest){
        return new ResponseEntity<>(warehouseReceiptsService.update(warehouseReceiptsStatusUpdateRequest),HttpStatus.OK);
    }

    @GetMapping(path = "/info/{id}")
    public ResponseEntity<WarehouseReceiptsInfoResponse> info(@PathVariable Long id){
        return new ResponseEntity<>(warehouseReceiptsService.info(id),HttpStatus.OK);
    }
}
