package com.rustam.modern_dentistry.service.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import com.rustam.modern_dentistry.dao.entity.settings.PriceCategory;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItemInsurance;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItemPrice;
import com.rustam.modern_dentistry.dao.repository.settings.InsuranceCompanyRepository;
import com.rustam.modern_dentistry.dao.repository.settings.PriceCategoryRepository;
import com.rustam.modern_dentistry.dao.repository.settings.operations.OperationTypeItemRepository;
import com.rustam.modern_dentistry.dto.request.create.OpTypeItemInsurances;
import com.rustam.modern_dentistry.dto.request.create.Prices;
import com.rustam.modern_dentistry.dto.request.update.OpTypeItemInsuranceUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeItemPricesUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rustam.modern_dentistry.mapper.settings.operations.OperationTypeItemMapper.OP_TYPE_ITEM_MAPPER;

@Service
@RequiredArgsConstructor
public class OperationTypeItemHelperService {
    private final OperationTypeItemRepository repository;
    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final PriceCategoryRepository priceCategoryRepository;

    protected OpTypeItem getOperationTypeItemById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("OperationType not found with ID: " + id)
        );
    }

    protected List<OpTypeItemInsurance> getOpTypeItemInsurances(List<OpTypeItemInsurances> request, OpTypeItem opTypeItem) {
        if (request != null) {
            List<Long> insuranceCompanyIds = request.stream().map(OpTypeItemInsurances::getInsuranceCompanyId).collect(Collectors.toList());
            Map<Long, InsuranceCompany> insuranceCompaniesMap = insuranceCompanyRepository.findByIdIn(insuranceCompanyIds)
                    .stream()
                    .collect(Collectors.toMap(InsuranceCompany::getId, insuranceCompany -> insuranceCompany));

            return request.stream()
                    .map(insuranceRequest -> {
                        var insuranceCompany = insuranceCompaniesMap.get(insuranceRequest.getInsuranceCompanyId());
                        var insurance = OP_TYPE_ITEM_MAPPER.toInsuranceEntity(insuranceRequest);
                        insurance.setOpTypeItem(opTypeItem);
                        insurance.setInsuranceCompany(insuranceCompany);
                        return insurance;
                    }).toList();
        }
        return null;
    }

    protected List<OpTypeItemPrice> getOpTypeItemPrices(List<Prices> request, OpTypeItem opTypeItem) {
        if (request != null) {
            List<Long> priceCategoryIds = request.stream().map(Prices::getPriceTypeId).collect(Collectors.toList());
            Map<Long, PriceCategory> priceCategoryMap = priceCategoryRepository.findByIdIn(priceCategoryIds)
                    .stream()
                    .collect(Collectors.toMap(PriceCategory::getId, priceCategory -> priceCategory));

            return request.stream()
                    .map(insuranceRequest -> {
                        var priceCategory = priceCategoryMap.get(insuranceRequest.getPriceTypeId());
                        var opTypeItemPrice = OP_TYPE_ITEM_MAPPER.toPriceCategoryEntity(insuranceRequest);
                        opTypeItemPrice.setOpTypeItem(opTypeItem);
                        opTypeItemPrice.setPriceCategory(priceCategory);
                        return opTypeItemPrice;
                    }).toList();
        }
        return null;
    }

    protected List<OpTypeItemReadResponse> getContent(List<OpTypeItem> operationTypes) {
        List<PriceCategory> allCategories = priceCategoryRepository.findAll();
        return operationTypes.stream()
                .map(opTypeItem -> {
                    var readDto = OP_TYPE_ITEM_MAPPER.toReadDto(opTypeItem);
                    readDto.setPrices(OP_TYPE_ITEM_MAPPER.mapPrices(opTypeItem.getPrices(), allCategories));
                    return readDto;
                })
                .collect(Collectors.toList());
    }

    protected List<OpTypeItemPrice> updateOpTypePrices(List<OpTypeItemPricesUpdateRequest> request, OpTypeItem opTypeItem) {
        Map<Long, OpTypeItemPrice> currentPrices = opTypeItem.getPrices().stream()
                .collect(Collectors.toMap(p -> p.getPriceCategory().getId(), p -> p));

        return request.stream()
                .filter(p -> p.getPrice() != null) // Null olmayanları götür
                .map(priceDto ->
                        currentPrices.containsKey(priceDto.getPriceCategoryId())
                                ? updateExistingPrice(currentPrices.get(priceDto.getPriceCategoryId()), priceDto.getPrice(), opTypeItem)
                                : createNewPrice(opTypeItem, priceDto.getPriceCategoryId(), priceDto.getPrice())
                )
                .collect(Collectors.toList());

    }

    protected List<OpTypeItemInsurance> updateOpTypeInsurance(List<OpTypeItemInsuranceUpdateRequest> request, OpTypeItem opTypeItem) {
        Map<Long, OpTypeItemInsurance> currentInsurances = opTypeItem.getInsurances().stream()
                .collect(Collectors.toMap(p -> p.getInsuranceCompany().getId(), p -> p));

        return request.stream()
                .filter(p -> p.getAmount() != null) // Null olmayanları götür
                .map(insuranceDto ->
                        currentInsurances.containsKey(insuranceDto.getInsuranceCompanyId())
                                ? updateExistingInsurance(currentInsurances.get(insuranceDto.getInsuranceCompanyId()), insuranceDto.getAmount(), opTypeItem)
                                : createNewInsurance(opTypeItem, insuranceDto)
                )
                .collect(Collectors.toList());
    }

    private OpTypeItemPrice updateExistingPrice(OpTypeItemPrice existingPrice,
                                                BigDecimal newPrice,
                                                OpTypeItem opTypeItem) {
        existingPrice.setPrice(newPrice);
        existingPrice.setOpTypeItem(opTypeItem);
        return existingPrice;
    }

    private OpTypeItemPrice createNewPrice(OpTypeItem opTypeItem, Long categoryId, BigDecimal price) {
        PriceCategory category = PriceCategory.builder().id(categoryId).build();
        return new OpTypeItemPrice(null, price, category, opTypeItem);
    }

    private OpTypeItemInsurance updateExistingInsurance(OpTypeItemInsurance existingInsurance,
                                                        BigDecimal newAmount,
                                                        OpTypeItem opTypeItem) {
        existingInsurance.setAmount(newAmount);
        existingInsurance.setOpTypeItem(opTypeItem);
        return existingInsurance;
    }

    private OpTypeItemInsurance createNewInsurance(OpTypeItem opTypeItem,
                                                   OpTypeItemInsuranceUpdateRequest request) {
        InsuranceCompany company = InsuranceCompany.builder().id(request.getInsuranceCompanyId()).build();
        return new OpTypeItemInsurance(null, request.getName(), request.getAmount(), opTypeItem, company);
    }
}
