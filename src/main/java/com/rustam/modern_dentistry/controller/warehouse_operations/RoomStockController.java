package com.rustam.modern_dentistry.controller.warehouse_operations;

import com.rustam.modern_dentistry.dto.request.read.RoomStockRequest;
import com.rustam.modern_dentistry.dto.response.read.RoomStockResponse;
import com.rustam.modern_dentistry.service.warehouse_operations.RoomStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/room-stock")
@RequiredArgsConstructor
public class RoomStockController {

    private final RoomStockService roomStockService;

    @PostMapping(path = "/search")
    public ResponseEntity<List<RoomStockResponse>> search(@RequestBody RoomStockRequest roomStockRequest){
        return new ResponseEntity<>(roomStockService.search(roomStockRequest), HttpStatus.OK);
    }
}
