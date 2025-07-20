package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouseProduct;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.OrderFromWarehouseProductRepository;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderFromWarehouseProductService {

    OrderFromWarehouseProductRepository orderFromWarehouseProductRepository;

    public void saveOrderFromWarehouseProduct(OrderFromWarehouseProduct matchedProduct) {
        orderFromWarehouseProductRepository.save(matchedProduct);
    }

    public OrderFromWarehouseProduct findById(Long orderFromWarehouseProductId) {
        return orderFromWarehouseProductRepository.findById(orderFromWarehouseProductId)
                .orElseThrow(() -> new NotFoundException("No such order from warehouse product found."));
    }
}
