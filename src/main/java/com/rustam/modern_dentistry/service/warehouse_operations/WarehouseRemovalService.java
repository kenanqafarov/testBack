package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.enums.status.PendingStatus;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouseProduct;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemoval;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemovalProduct;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.WarehouseRemovalRepository;
import com.rustam.modern_dentistry.dto.OutOfTheWarehouseDto;
import com.rustam.modern_dentistry.dto.request.create.WarehouseRemovalCreateRequest;
import com.rustam.modern_dentistry.dto.request.create.WarehouseRemovalProductCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.WarehouseRemovalProductSearchRequest;
import com.rustam.modern_dentistry.dto.response.create.WarehouseRemovalCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.WarehouseRemovalReadResponse;
import com.rustam.modern_dentistry.exception.custom.AmountSendException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.warehouse_operations.WarehouseRemovalMapper;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.warehouse_operations.WarehouseRemovalSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseRemovalService {

    WarehouseRemovalRepository warehouseRemovalRepository;
    WarehouseRemovalMapper warehouseRemovalMapper;

    public void save(WarehouseRemoval warehouseRemoval) {
        warehouseRemovalRepository.save(warehouseRemoval);
    }

    public WarehouseRemoval findById(Long id) {
        return warehouseRemovalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such warehouse removal found."));
    }

    @Transactional
    public List<WarehouseRemovalReadResponse> read() {
        List<WarehouseRemoval> warehouseRemovals = warehouseRemovalRepository.findAll();
        return warehouseRemovalMapper.toDtos(warehouseRemovals);
    }

    @Transactional
    public List<WarehouseRemovalReadResponse> search(WarehouseRemovalProductSearchRequest warehouseRemovalSearchRequest) {
        List<WarehouseRemoval> warehouseRemovals = warehouseRemovalRepository.findAll(WarehouseRemovalSpecification.filterBy(warehouseRemovalSearchRequest));
        return warehouseRemovalMapper.toDtos(warehouseRemovals);
    }

    @Transactional
    public WarehouseRemovalReadResponse info(Long id) {
        WarehouseRemoval warehouseRemoval = findById(id);
        return warehouseRemovalMapper.toDto(warehouseRemoval);
    }
}