package com.rustam.modern_dentistry.controller.warehouse_operations;

import com.rustam.modern_dentistry.dto.request.create.OrderFromWarehouseCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.OrderFromWarehouseSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.OrderFromWarehouseUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.OrderFromWarehouseReadResponse;
import com.rustam.modern_dentistry.dto.response.read.OrderFromWarehouseResponse;
import com.rustam.modern_dentistry.service.warehouse_operations.OrderFromWarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/order-from-warehouse")
@RequiredArgsConstructor
public class OrderFromWarehouseController {

    private final OrderFromWarehouseService orderFromWarehouseService;

    @PostMapping(path = "/create")
    public ResponseEntity<OrderFromWarehouseResponse> create(@Valid @RequestBody OrderFromWarehouseCreateRequest orderFromWarehouseCreateRequest){
        return new ResponseEntity<>(orderFromWarehouseService.create(orderFromWarehouseCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<OrderFromWarehouseReadResponse>> read(){
        return new ResponseEntity<>(orderFromWarehouseService.read(),HttpStatus.OK);
    }

    @GetMapping(path = "/info/{id}")
    public ResponseEntity<OrderFromWarehouseResponse> info(@PathVariable Long id){
        return new ResponseEntity<>(orderFromWarehouseService.info(id),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<OrderFromWarehouseReadResponse>> search(@RequestBody OrderFromWarehouseSearchRequest orderFromWarehouseSearchRequest){
        return new ResponseEntity<>(orderFromWarehouseService.search(orderFromWarehouseSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<OrderFromWarehouseResponse> update(@RequestBody OrderFromWarehouseUpdateRequest orderFromWarehouseUpdateRequest){
        return new ResponseEntity<>(orderFromWarehouseService.update(orderFromWarehouseUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderFromWarehouseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/delete-order-from-warehouse-product/order-from-warehouse/{id}/order-from-warehouse-product/{productId}")
    public ResponseEntity<Void> deleteOrderFromWarehouseProduct(@PathVariable Long id, @PathVariable Long productId){
        orderFromWarehouseService.deleteOrderFromWarehouseProduct(id,productId);
        return ResponseEntity.ok().build();
    }

}
