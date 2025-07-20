package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dto.request.read.WarehouseSearchRequest;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReadResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WarehouseService {

    WarehouseEntryService warehouseEntryService;

    public List<WarehouseReadResponse> read() {
        return warehouseEntryService.readWarehouse();
    }

    public List<WarehouseReadResponse> search(WarehouseSearchRequest warehouseSearchRequest) {
        return warehouseEntryService.searchWarehouse(warehouseSearchRequest);
    }
}
