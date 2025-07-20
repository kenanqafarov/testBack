package com.rustam.modern_dentistry.service.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpType;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dao.repository.settings.operations.OperationTypeItemRepository;
import com.rustam.modern_dentistry.dto.request.create.OpTypeItemCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.OpTypeItemSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeItemUpdateRequest;
import com.rustam.modern_dentistry.dto.response.excel.OpTypeItemExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadByIdResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.operations.OpTypeItemSearchSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;
import static com.rustam.modern_dentistry.mapper.settings.operations.OperationTypeItemMapper.OP_TYPE_ITEM_MAPPER;
import static org.springframework.data.domain.PageRequest.of;

@Service
@RequiredArgsConstructor
public class OperationTypeItemService {
    private final OperationTypeItemRepository repository;
    private final OperationTypeItemHelperService helperService;

    public void create(OpTypeItemCreateRequest request) {
        var opTypeItem = OP_TYPE_ITEM_MAPPER.toEntity(request);
        var prices = helperService.getOpTypeItemPrices(request.getPrices(), opTypeItem);
        var insurances = helperService.getOpTypeItemInsurances(request.getInsurances(), opTypeItem);
        opTypeItem.setPrices(prices);
        opTypeItem.setInsurances(insurances);
        opTypeItem.setOpType(OpType.builder().id(request.getOpTypeId()).build());
        repository.save(opTypeItem);
    }

    public PageResponse<OpTypeItemReadResponse> read(Long id, PageCriteria pageCriteria) {
        var response = repository.findByOpTypeId(id, of(pageCriteria.getPage(), pageCriteria.getCount()));
        var list = helperService.getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), list);
    }

    public PageResponse<OpTypeItemReadResponse> search(OpTypeItemSearchRequest request, PageCriteria pageCriteria) {
        var response = repository.findAll(
                OpTypeItemSearchSpec.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var content = helperService.getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), content);
    }

    public OpTypeItemReadByIdResponse readById(Long id) {
        var operationType = helperService.getOperationTypeItemById(id);
        var toReadDto = OP_TYPE_ITEM_MAPPER.toReadByIdDto(operationType);
        toReadDto.setInsurances(repository.findInsurancesByOpTypeItemId(id));
        toReadDto.setPrices(repository.findPricesByOpTypeItemId(id));
        return toReadDto;
    }

    public void update(Long id, OpTypeItemUpdateRequest request) {
        var opTypeItem = helperService.getOperationTypeItemById(id);
        OP_TYPE_ITEM_MAPPER.updateOpTypeItem(opTypeItem, request);

        if (request.getPrices() != null) {
            opTypeItem.setPrices(helperService.updateOpTypePrices(request.getPrices(), opTypeItem));
        }

        if (request.getInsurances() != null) {
            opTypeItem.setInsurances(helperService.updateOpTypeInsurance(request.getInsurances(), opTypeItem));
        }
        repository.save(opTypeItem);
    }

    public void updateStatus(Long id) {
        var operationType = helperService.getOperationTypeItemById(id);
        var status = operationType.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        operationType.setStatus(status);
        repository.save(operationType);
    }

    public void delete(Long id) {
        var operationType = helperService.getOperationTypeItemById(id);
        repository.delete(operationType);
    }

    public InputStreamResource exportReservationsToExcel() {
        List<OpTypeItem> operations = repository.findAll();
        var list = operations.stream().map(OP_TYPE_ITEM_MAPPER::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, OpTypeItemExcelResponse.class);
        return new InputStreamResource(excelFile);
    }
}
