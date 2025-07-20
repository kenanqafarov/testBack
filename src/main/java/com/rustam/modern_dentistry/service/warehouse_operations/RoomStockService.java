package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dto.request.read.RoomStockRequest;
import com.rustam.modern_dentistry.dto.response.read.RoomStockResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoomStockService {

    OrderFromWarehouseService orderFromWarehouseService;

    public List<RoomStockResponse> search(RoomStockRequest roomStockRequest) {
        return orderFromWarehouseService.search(roomStockRequest);
    }
}
