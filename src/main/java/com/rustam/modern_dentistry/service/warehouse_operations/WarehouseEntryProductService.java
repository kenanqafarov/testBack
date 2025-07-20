package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseEntryProduct;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.WarehouseEntryProductRepository;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;

import com.rustam.modern_dentistry.exception.custom.ProductDoesnotQuantityThatMuchException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WarehouseEntryProductService {

    WarehouseEntryProductRepository warehouseEntryProductRepository;

    public void decreaseProductQuantity(Long productId, long quantityToDecrease) {
        WarehouseEntryProduct warehouseEntryProduct = findById(productId);

        if (warehouseEntryProduct.getQuantity() < quantityToDecrease) {
            throw new ProductDoesnotQuantityThatMuchException("Anbarda kifayət qədər məhsul yoxdur");
        }

        updateProductQuantities(warehouseEntryProduct, quantityToDecrease);
    }

    public WarehouseEntryProduct findById(Long productId) {
        return warehouseEntryProductRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("such warehouse entry product not found"));
    }

    public WarehouseEntryProduct findByIdOrNull(Long productId) {
        return warehouseEntryProductRepository.findById(productId).orElse(null);
    }


    private void updateProductQuantities(WarehouseEntryProduct product, long quantityToDecrease) {
        long currentQuantity = product.getQuantity();

        product.setQuantity(currentQuantity - quantityToDecrease);

        product.setUsedQuantity(product.getUsedQuantity() + quantityToDecrease);

        warehouseEntryProductRepository.save(product);
    }

    public void increaseProductQuantity(Long productId, long quantityToIncrease) {
        WarehouseEntryProduct warehouseEntryProduct = findById(productId);
        warehouseEntryProduct.setQuantity(warehouseEntryProduct.getQuantity() + quantityToIncrease);
        warehouseEntryProduct.setUsedQuantity(warehouseEntryProduct.getUsedQuantity() - quantityToIncrease);
        warehouseEntryProductRepository.save(warehouseEntryProduct);
    }

    public WarehouseEntryProduct findAllByIdAndWarehouseEntryIdAndCategoryIdAndProductId(Long id,Long warehouseEntryId, Long categoryId, Long productId) {
        List<WarehouseEntryProduct> products =
                warehouseEntryProductRepository.findAllByIdAndWarehouseEntryIdAndCategoryIdAndProductId(
                        id,warehouseEntryId,categoryId,productId);

        return products.stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No matching WarehouseEntryProduct found"));
    }

    public void delete(WarehouseEntryProduct entryProduct) {
        warehouseEntryProductRepository.delete(entryProduct);
    }

    public void save(WarehouseEntryProduct entryProduct) {
        warehouseEntryProductRepository.save(entryProduct);
    }
}
