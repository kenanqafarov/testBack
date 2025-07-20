package com.rustam.modern_dentistry.service.settings.operations;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpType;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeInsurance;
import com.rustam.modern_dentistry.dao.repository.settings.operations.OperationTypeRepository;
import com.rustam.modern_dentistry.dto.request.create.OpTypeCreateRequest;
import com.rustam.modern_dentistry.dto.request.create.OpTypeInsuranceRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.OperationTypeSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.excel.OperationTypeExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.service.settings.InsuranceCompanyService;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.operations.OpTypeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rustam.modern_dentistry.mapper.settings.operations.OperationTypeMapper.OP_TYPE_MAPPER;

@Service
@RequiredArgsConstructor
public class OperationTypeService {
    private final OperationTypeRepository repository;
    private final InsuranceCompanyService insuranceCompanyService;

    @Transactional
    public void create(OpTypeCreateRequest request) {
        var opType = OP_TYPE_MAPPER.toEntity(request);
        if (request.getInsurances() != null) {
            var insurances = getInsurances(request.getInsurances(), opType);
            opType.setInsurances(insurances);
        }
        repository.save(opType);
    }

    public PageResponse<OpTypeReadResponse> read(PageCriteria pageCriteria) {
        var response = repository.findAll(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var content = getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), content);
    }

    public PageResponse<OpTypeReadResponse> search(OperationTypeSearchRequest request, PageCriteria pageCriteria) {
        var response = repository.findAll(
                OpTypeSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var content = getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), content);
    }

    public OpTypeReadResponse readById(Long id) {
        var operationType = getOperationTypeById(id);
        var opInsurances = repository.findByOpTypeId(id);
        var readDto = OP_TYPE_MAPPER.toReadByIdDto(operationType);
        readDto.setInsurances(opInsurances);
        return readDto;
    }

    public void update(Long id, OpTypeUpdateRequest request) {
        var opType = getOperationTypeById(id);
        OP_TYPE_MAPPER.updateOpType(opType, request);
        if (request.getInsurances() != null) {
            var updatedInsurances = updateOpTypeInsurance(request.getInsurances(), opType);
            opType.setInsurances(updatedInsurances);
        }
        repository.save(opType);
    }

    public void updateStatus(Long id) {
        var operationType = getOperationTypeById(id);
        var status = operationType.getStatus() == Status.ACTIVE ? Status.PASSIVE : Status.ACTIVE;
        operationType.setStatus(status);
        repository.save(operationType);
    }

    public void delete(Long id) {
        var operationType = getOperationTypeById(id);
        repository.delete(operationType);
    }

    public InputStreamResource exportReservationsToExcel() {
        List<OpType> reservations = repository.findAll();
        var list = reservations.stream().map(OP_TYPE_MAPPER::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, OperationTypeExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    // Helper methods
    private List<OpTypeInsurance> getInsurances(List<OpTypeInsuranceRequest> insurances, OpType opType) {
        return insurances.stream()
                .map(insuranceRequest -> {
                    var insurance = OP_TYPE_MAPPER.toInsuranceEntity(insuranceRequest);
                    insurance.setOpType(opType);
                    insurance.setInsuranceCompany(insuranceCompanyService.getInsuranceById(insuranceRequest.getInsuranceCompanyId()));
                    return insurance;
                }).toList();
    }

    protected OpType getOperationTypeById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("OperationType not found with ID: " + id)
        );
    }

    private List<OpTypeReadResponse> getContent(List<OpType> operationTypes) {
        return operationTypes
                .stream()
                .map(OP_TYPE_MAPPER::toReadDto)
                .toList();
    }

    public OpType findByCategoryName(String operationCategoryName) {
        return repository.findByCategoryName(operationCategoryName)
                .orElseThrow(() -> new NotFoundException("OperationType not found with name: " + operationCategoryName));
    }

    private List<OpTypeInsurance> updateOpTypeInsurance(List<OpTypeInsuranceRequest> request, OpType opType) {
        Map<Long, OpTypeInsurance> currentInsurances = opType.getInsurances().stream()
                .collect(Collectors.toMap(p -> p.getInsuranceCompany().getId(), p -> p));

        return request.stream()
                .filter(p -> p.getDeductiblePercentage() != null) // Null olmayanları götür
                .map(insuranceDto ->
                        currentInsurances.containsKey(insuranceDto.getInsuranceCompanyId())
                                ? updateExistingInsurance(currentInsurances.get(insuranceDto.getInsuranceCompanyId()), insuranceDto, opType)
                                : createNewInsurance(opType, insuranceDto)
                )
                .collect(Collectors.toList());
    }

    private OpTypeInsurance updateExistingInsurance(OpTypeInsurance existingInsurance,
                                                    OpTypeInsuranceRequest request,
                                                    OpType opType) {
        existingInsurance.setDeductiblePercentage(request.getDeductiblePercentage());
        existingInsurance.setOpType(opType);
        return existingInsurance;
    }

    private OpTypeInsurance createNewInsurance(OpType opType,
                                               OpTypeInsuranceRequest request) {
        InsuranceCompany company = InsuranceCompany.builder().id(request.getInsuranceCompanyId()).build();
        return new OpTypeInsurance(null, request.getDeductiblePercentage(), opType, company);
    }
}
