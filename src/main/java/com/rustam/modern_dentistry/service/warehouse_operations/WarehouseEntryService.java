package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.settings.product.Product;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseEntry;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseEntryProduct;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.WarehouseEntryRepository;
import com.rustam.modern_dentistry.dto.request.create.WarehouseEntryCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.WarehouseEntrySearchRequest;
import com.rustam.modern_dentistry.dto.request.read.WarehouseSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.WarehouseEntryProductUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.WarehouseEntryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.WarehouseEntryCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.*;
import com.rustam.modern_dentistry.exception.custom.WarehouseEntryNotFoundException;
import com.rustam.modern_dentistry.mapper.warehouse_operations.WarehouseEntryMapper;
import com.rustam.modern_dentistry.service.settings.product.ProductService;
import com.rustam.modern_dentistry.util.specification.warehouse_operations.WarehouseEntrySpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WarehouseEntryService {

    WarehouseEntryRepository warehouseEntryRepository;
    WarehouseEntryMapper warehouseEntryMapper;
    ProductService productService;
    OrderFromWarehouseService orderFromWarehouseService;

    @Transactional
    public WarehouseEntryCreateResponse create(WarehouseEntryCreateRequest request) {
        WarehouseEntry entry = WarehouseEntry.builder()
                .date(request.getDate())
                .time(request.getTime())
                .description(request.getDescription())
                .build();
        List<WarehouseEntryProduct> entryProducts = buildWarehouseEntryProducts(request, entry);

        entry.setNumber(entryProducts.size());
        entry.setWarehouseEntryProducts(entryProducts);
        entry.setSumPrice(calculateSumPrice(entryProducts));

        warehouseEntryRepository.save(entry);

        return buildWarehouseEntryResponse(entry, entryProducts);
    }

    private WarehouseEntryCreateResponse buildWarehouseEntryResponse(WarehouseEntry entry, List<WarehouseEntryProduct> products) {
        List<WarehouseEntryProductResponse> productResponses = products.stream()
                .map(p -> WarehouseEntryProductResponse.builder()
                        .categoryName(p.getCategoryName())
                        .productName(p.getProductName())
                        .productTitle(p.getProductTitle())
                        .price(p.getPrice())
                        .quantity(p.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return WarehouseEntryCreateResponse.builder()
                .date(entry.getDate())
                .time(entry.getTime())
                .description(entry.getDescription())
                .warehouseEntryProducts(productResponses)
                .sumPrice(entry.getSumPrice())
                .build();
    }


    private BigDecimal calculateSumPrice(List<WarehouseEntryProduct> products) {
        return products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<WarehouseEntryProduct> buildWarehouseEntryProducts(WarehouseEntryCreateRequest request, WarehouseEntry entry) {
        return request.getWarehouseEntryProductCreateRequests().stream()
                .map(dto -> {
                    Product product = productService.findByIdAndCategoryId(dto.getProductId(), dto.getCategoryId());
                    return WarehouseEntryProduct.builder()
                            .categoryId(dto.getCategoryId())
                            .productId(dto.getProductId())
                            .quantity(dto.getQuantity())
                            .usedQuantity(0L)
                            .price(dto.getPrice())
                            .productName(product.getProductName())
                            .categoryName(product.getCategory().getCategoryName())
                            .productTitle(product.getProductTitle())
                            .warehouseEntry(entry)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<WarehouseEntryReadResponse> read() {
        List<WarehouseEntry> entries = warehouseEntryRepository.findAll();

        return entries.stream()
                .map(entry -> WarehouseEntryReadResponse.builder()
                        .id(entry.getId())
                        .time(entry.getTime())
                        .date(entry.getDate())
                        .number(entry.getNumber())
                        .sumPrice(entry.getSumPrice())
                        .build())
                .toList();
    }

    public List<WarehouseEntryReadResponse> search(WarehouseEntrySearchRequest warehouseEntrySearchRequest) {
        List<WarehouseEntry> warehouseEntries = warehouseEntryRepository.findAll(WarehouseEntrySpecification.filterBy(warehouseEntrySearchRequest));
        return warehouseEntries.stream()
                .map(entry -> WarehouseEntryReadResponse.builder()
                        .id(entry.getId())
                        .time(entry.getTime())
                        .date(entry.getDate())
                        .number(entry.getNumber())
                        .sumPrice(entry.getSumPrice())
                        .build())
                .toList();
    }

    public WarehouseEntry findById(Long id){
        return warehouseEntryRepository.findById(id)
                .orElseThrow(() -> new WarehouseEntryNotFoundException("No such warehouse access operation found."));
    }

    @Transactional
    public WarehouseEntryResponse info(Long id) {
        WarehouseEntry warehouseEntry = findById(id);
        return warehouseEntryMapper.toDto(warehouseEntry);
    }

    @Transactional
    public void delete(Long id) {
        WarehouseEntry warehouseEntry = findById(id);
        List<WarehouseEntryProduct> warehouseEntryProducts = warehouseEntry.getWarehouseEntryProducts();

        for (WarehouseEntryProduct entryProduct : warehouseEntryProducts) {
            List<OrderFromWarehouse> ordersFromWarehouse = findOrdersByProductId(entryProduct.getProductId());

            for (OrderFromWarehouse order : ordersFromWarehouse) {
                deleteOrderFromWarehouse(order);
            }
        }

        warehouseEntryRepository.delete(warehouseEntry);
    }

    private List<OrderFromWarehouse> findOrdersByProductId(Long productId) {
        return orderFromWarehouseService.findByProductId(productId);
    }

    private void deleteOrderFromWarehouse(OrderFromWarehouse order) {
        orderFromWarehouseService.delete(order.getId());
    }


    @Transactional
    public WarehouseEntryCreateResponse update(WarehouseEntryUpdateRequest request) {
        WarehouseEntry entry = findById(request.getId());

        updateFieldIfPresent(request.getDate(), entry::setDate);
        updateFieldIfPresent(request.getTime(), entry::setTime);
        updateFieldIfPresent(request.getDescription(), entry::setDescription);

        if (hasProducts(request)) {
            List<WarehouseEntryProduct> entryProducts = buildWarehouseEntryUpdateProducts(request, entry);
            entry.getWarehouseEntryProducts().clear();
            entry.getWarehouseEntryProducts().addAll(entryProducts);
            entry.setNumber(entryProducts.size());
            entry.setSumPrice(calculateSumPrice(entryProducts));
        }

        warehouseEntryRepository.save(entry);

        return buildWarehouseEntryResponse(entry, entry.getWarehouseEntryProducts());
    }

    private boolean hasProducts(WarehouseEntryUpdateRequest request) {
        return request.getWarehouseEntryProductUpdateRequests() != null &&
                !request.getWarehouseEntryProductUpdateRequests().isEmpty();
    }

    private <T> void updateFieldIfPresent(T newValue, Consumer<T> setter) {
        if (newValue != null && !(newValue instanceof String str && str.isBlank())) {
            setter.accept(newValue);
        }
    }

    private List<WarehouseEntryProduct> buildWarehouseEntryUpdateProducts(WarehouseEntryUpdateRequest request, WarehouseEntry entry) {
        Map<Long, WarehouseEntryProduct> existingProductsMap = entry.getWarehouseEntryProducts()
                .stream()
                .collect(Collectors.toMap(WarehouseEntryProduct::getId, Function.identity()));

        List<WarehouseEntryProduct> finalList = new ArrayList<>();

        Set<Long> updatedIds = new HashSet<>();

        for (WarehouseEntryProductUpdateRequest dto : request.getWarehouseEntryProductUpdateRequests()) {
            Product product = productService.findByIdAndCategoryId(dto.getProductId(), dto.getCategoryId());

            if (dto.getWarehouseEntryProductId() != null && existingProductsMap.containsKey(dto.getWarehouseEntryProductId())) {

                WarehouseEntryProduct existing = existingProductsMap.get(dto.getWarehouseEntryProductId());

                updateFieldIfPresent(dto.getCategoryId(), existing::setCategoryId);
                updateFieldIfPresent(dto.getProductId(), existing::setProductId);
                updateFieldIfPresent(dto.getQuantity(), existing::setQuantity);
                updateFieldIfPresent(dto.getPrice(), existing::setPrice);

                existing.setProductName(product.getProductName());
                existing.setCategoryName(product.getCategory().getCategoryName());
                existing.setProductTitle(product.getProductTitle());

                finalList.add(existing);
                updatedIds.add(dto.getWarehouseEntryProductId());
            } else {

                WarehouseEntryProduct newProduct = WarehouseEntryProduct.builder()
                        .categoryId(dto.getCategoryId())
                        .productId(dto.getProductId())
                        .quantity(dto.getQuantity())
                        .price(dto.getPrice())
                        .productName(product.getProductName())
                        .categoryName(product.getCategory().getCategoryName())
                        .productTitle(product.getProductTitle())
                        .warehouseEntry(entry)
                        .build();
                finalList.add(newProduct);
            }
        }

        for (WarehouseEntryProduct old : existingProductsMap.values()) {
            if (!updatedIds.contains(old.getId())) {
                finalList.add(old);
            }
        }

        return finalList;
    }

    @Transactional
    public void deleteEntryProduct(Long entryId, Long productId) {
        WarehouseEntry entry = warehouseEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        entry.getWarehouseEntryProducts().removeIf(p -> p.getId().equals(productId));

        entry.setNumber(entry.getWarehouseEntryProducts().size());

        BigDecimal sumPrice = entry.getWarehouseEntryProducts().stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        entry.setSumPrice(sumPrice);

        warehouseEntryRepository.save(entry);
    }


    public List<WarehouseReadResponse> readWarehouse() {
        return warehouseEntryRepository.readWarehouse().stream()
                .map(p -> new WarehouseReadResponse(
                        p.getId(),
                        p.getCategoryName(),
                        p.getProductName(),
                        p.getProductNo(),
                        p.getSumQuantity()
                ))
                .toList();
    }

    public List<WarehouseReadResponse> searchWarehouse(WarehouseSearchRequest request) {
        List<WarehouseReadProjection> projections = warehouseEntryRepository.readWarehouse(
                request.getCategoryId(),
                request.getProductName(),
                request.getProductNo()
        );

        return projections.stream()
                .map(p -> new WarehouseReadResponse(
                        p.getId(),
                        p.getCategoryName(),
                        p.getProductName(),
                        p.getProductNo(),
                        p.getSumQuantity()
                ))
                .collect(Collectors.toList());
    }
}
