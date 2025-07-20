package com.rustam.modern_dentistry.service.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpType;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.Teeth;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethOperation;
import com.rustam.modern_dentistry.dao.repository.settings.teeth.TeethOperationRepository;
import com.rustam.modern_dentistry.dto.request.create.CreateTeethOperationRequest;
import com.rustam.modern_dentistry.dto.request.create.OpTypeAndItemRequest;
import com.rustam.modern_dentistry.dto.request.read.SearchTeethOperationRequest;
import com.rustam.modern_dentistry.dto.request.update.UpdateTeethOperationRequest;
import com.rustam.modern_dentistry.dto.response.read.TeethOperationResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.exception.custom.TeethOperationNotFoundException;
import com.rustam.modern_dentistry.service.settings.operations.OperationTypeService;
import com.rustam.modern_dentistry.util.specification.settings.teeth.TeethOperationSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TeethOperationService {

    TeethOperationRepository teethOperationRepository;
    TeethService teethService;
    OperationTypeService operationTypeService;

    @Transactional
    public void create(CreateTeethOperationRequest createTeethOperationRequest) {
        Teeth teeth = teethService.findById(createTeethOperationRequest.getTeethId());

        for (OpTypeAndItemRequest opTypeAndItemRequest : createTeethOperationRequest.getOpTypeAndItemRequests()) {
            String fullOperationName = opTypeAndItemRequest.getOperationName();
            String[] parts = fullOperationName.split("-");
            String categoryName = parts[0];
            String operationName = parts[1];

            OpType opType = operationTypeService.findByCategoryName(categoryName);
            OpTypeItem opTypeItem = opType.getOpTypeItems().stream()
                    .filter(item -> item.getOperationName().equals(operationName))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("OpTypeItem tapılmadı!"));

            TeethOperation teethOperation = TeethOperation.builder()
                    .teeth(teeth)
                    .opTypeItem(opTypeItem)
                    .build();

            teethOperationRepository.save(teethOperation);
        }
    }

    public List<TeethOperationResponse> read() {
        return teethOperationRepository.findAllTeethOperations();
    }

    public TeethOperation findById(Long id){
        return teethOperationRepository.findById(id)
                .orElseThrow(() -> new TeethOperationNotFoundException("It is not found in dental operation."));
    }

    @Transactional
    public TeethOperationResponse update(UpdateTeethOperationRequest updateTeethOperationRequest) {
        TeethOperation teethOperation = findById(updateTeethOperationRequest.getId());

        if (updateTeethOperationRequest.getTeethId() != null) {
            Teeth teeth = teethService.findById(updateTeethOperationRequest.getTeethId());
            teethOperation.setTeeth(teeth);
        }

        if (updateTeethOperationRequest.getOpTypeAndItemRequests() != null) {
            OpTypeAndItemRequest opTypeAndItemRequest = updateTeethOperationRequest.getOpTypeAndItemRequests().stream()
                    .findFirst()
                    .orElse(null);

            if (opTypeAndItemRequest != null) {
                String fullOperationName = opTypeAndItemRequest.getOperationName();
                String[] parts = fullOperationName.split("_");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Düzgün format deyil! Format: categoryName_operationName olmalıdır");
                }

                String categoryName = parts[0];
                String operationName = parts[1];

                OpType opType = operationTypeService.findByCategoryName(categoryName);
                OpTypeItem opTypeItem = opType.getOpTypeItems().stream()
                        .filter(item -> item.getOperationName().equals(operationName))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("OpTypeItem tapılmadı!"));

                teethOperation.setOpTypeItem(opTypeItem);
            }
        }

        teethOperationRepository.save(teethOperation);
        return TeethOperationResponse.builder()
                .Id(teethOperation.getId())
                .operationName(teethOperation.getOpTypeItem().getOperationName())
                .build();
    }

    public void delete(Long id) {
        TeethOperation teethOperation = findById(id);
        teethOperationRepository.delete(teethOperation);
    }

    public List<TeethOperationResponse> search(SearchTeethOperationRequest searchTeethOperationRequest) {
        List<TeethOperation> teethOperations = teethOperationRepository.findAll(TeethOperationSpecification.filterBy(searchTeethOperationRequest));
        return teethOperations.stream()
                .map(teethOperation -> TeethOperationResponse.builder()
                        .Id(teethOperation.getId())
                        .operationName(teethOperation.getOpTypeItem().getOperationName())
                        .build())
                .toList();
    }
}
