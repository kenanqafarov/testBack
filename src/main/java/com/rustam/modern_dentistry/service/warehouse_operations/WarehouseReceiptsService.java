package com.rustam.modern_dentistry.service.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseReceipts;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemoval;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemovalProduct;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.WarehouseReceiptsRepository;
import com.rustam.modern_dentistry.dto.OutOfTheWarehouseDto;
import com.rustam.modern_dentistry.dto.request.read.WarehouseReceiptsRequest;
import com.rustam.modern_dentistry.dto.request.update.WarehouseReceiptsStatusUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReceiptsInfoResponse;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReceiptsResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.warehouse_operations.WarehouseReceiptsMapper;
import com.rustam.modern_dentistry.util.specification.warehouse_operations.WarehouseReceiptsSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WarehouseReceiptsService {

    WarehouseReceiptsRepository warehouseReceiptsRepository;
    WarehouseReceiptsMapper warehouseReceiptsMapper;

    public List<WarehouseReceiptsResponse> search(WarehouseReceiptsRequest warehouseReceiptsRequest) {
        List<WarehouseReceipts> warehouseReceipts = warehouseReceiptsRepository.findAll(WarehouseReceiptsSpecification.filterBy(warehouseReceiptsRequest));
        return warehouseReceiptsMapper.toDtos(warehouseReceipts);
    }

    public WarehouseReceipts findById(Long warehouseReceiptsId) {
        return warehouseReceiptsRepository.findById(warehouseReceiptsId)
                .orElseThrow(() -> new NotFoundException("No such warehouse receipts found."));
    }

    @Transactional
    public WarehouseReceiptsResponse update(WarehouseReceiptsStatusUpdateRequest warehouseReceiptsStatusUpdateRequest) {
        WarehouseReceipts warehouseReceipts = findById(warehouseReceiptsStatusUpdateRequest.getId());
        warehouseReceipts.setPendingStatus(warehouseReceiptsStatusUpdateRequest.getStatus());
        for (WarehouseRemovalProduct warehouseRemovalProducts : warehouseReceipts.getWarehouseRemovalProducts()){
            warehouseRemovalProducts.setPendingStatus(warehouseReceiptsStatusUpdateRequest.getStatus());
        }
        warehouseReceiptsRepository.save(warehouseReceipts);
        return warehouseReceiptsMapper.toDto(warehouseReceipts);
    }


    public WarehouseReceipts save(WarehouseReceipts warehouseReceipts) {
       return warehouseReceiptsRepository.save(warehouseReceipts);
    }

    public Optional<WarehouseReceipts> findByGroupId(String groupId) {
        return warehouseReceiptsRepository.findByGroupId(groupId);
    }

    @Transactional
    public WarehouseReceiptsInfoResponse info(Long id) {
        WarehouseReceipts warehouseReceipts = findById(id);
        return buildToResponse(warehouseReceipts);
    }

    private WarehouseReceiptsInfoResponse buildToResponse(WarehouseReceipts warehouseReceipts) {
        WarehouseReceiptsInfoResponse warehouseReceiptsInfoResponse = buildWarehouseReceiptsBaseInfo(warehouseReceipts);
        List<OutOfTheWarehouseDto> outOfTheWarehouseDtoList = buildOutOfTheWarehouseDtoList(warehouseReceipts);
        warehouseReceiptsInfoResponse.setOutOfTheWarehouseDtos(outOfTheWarehouseDtoList);

        warehouseReceiptsInfoResponse.setDescription(
                warehouseReceipts.getWarehouseRemovalProducts().stream()
                        .map(WarehouseRemovalProduct::getProductDescription)
                        .reduce((first, second) -> second)
                        .orElse(null)
        );

        return warehouseReceiptsInfoResponse;
    }

    private WarehouseReceiptsInfoResponse buildWarehouseReceiptsBaseInfo(WarehouseReceipts warehouseReceipts) {
        return WarehouseReceiptsInfoResponse.builder()
                .id(warehouseReceipts.getId())
                .orderQuantity(warehouseReceipts.getOrderQuantity())
                .date(warehouseReceipts.getDate())
                .room(warehouseReceipts.getRoom())
                .time(warehouseReceipts.getTime())
                .personWhoPlacedOrder(warehouseReceipts.getPersonWhoPlacedOrder())
                .incomingQuantity(warehouseReceipts.getSendQuantity())
                .pendingStatus(warehouseReceipts.getPendingStatus())
                .build();
    }

    private List<OutOfTheWarehouseDto> buildOutOfTheWarehouseDtoList(WarehouseReceipts warehouseReceipts) {
        return warehouseReceipts.getWarehouseRemovalProducts().stream()
                .map(this::buildOutOfTheWarehouseDto)
                .toList();
    }

    private OutOfTheWarehouseDto buildOutOfTheWarehouseDto(WarehouseRemovalProduct warehouseRemovalProduct) {
        return OutOfTheWarehouseDto.builder()
                .categoryName(warehouseRemovalProduct.getCategoryName())
                .productName(warehouseRemovalProduct.getProductName())
                .productDescription(warehouseRemovalProduct.getProductDescription())
                .sendQuantity(warehouseRemovalProduct.getSendAmount())
                .orderQuantity(warehouseRemovalProduct.getOrderAmount())
                .remainingQuantity(warehouseRemovalProduct.getRemainingAmount())
                .currentAmount(warehouseRemovalProduct.getCurrentAmount())
                .build();
    }
}
