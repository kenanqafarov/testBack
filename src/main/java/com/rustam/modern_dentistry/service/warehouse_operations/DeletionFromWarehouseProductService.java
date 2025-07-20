package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.DeletionFromWarehouseProduct;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.DeletionFromWarehouseProductRepository;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DeletionFromWarehouseProductService {

    DeletionFromWarehouseProductRepository deletionFromWarehouseProductRepository;

    public DeletionFromWarehouseProduct findById(Long id){
        return deletionFromWarehouseProductRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such deletion from warehouse product found."));
    }
}
