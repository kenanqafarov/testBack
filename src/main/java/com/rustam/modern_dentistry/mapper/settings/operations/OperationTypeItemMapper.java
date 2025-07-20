package com.rustam.modern_dentistry.mapper.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import com.rustam.modern_dentistry.dao.entity.settings.PriceCategory;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItemInsurance;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItemPrice;
import com.rustam.modern_dentistry.dto.request.update.OpTypeItemInsuranceUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeItemPricesUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeItemUpdateRequest;
import com.rustam.modern_dentistry.dto.request.create.OpTypeItemCreateRequest;
import com.rustam.modern_dentistry.dto.request.create.OpTypeItemInsurances;
import com.rustam.modern_dentistry.dto.request.create.Prices;
import com.rustam.modern_dentistry.dto.response.excel.OpTypeItemExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemPricesDto;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadByIdResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemReadResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OperationTypeItemMapper {
    OperationTypeItemMapper OP_TYPE_ITEM_MAPPER = Mappers.getMapper(OperationTypeItemMapper.class);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    @Mapping(target = "prices", ignore = true)
    @Mapping(target = "insurances", ignore = true)
    OpTypeItem toEntity(OpTypeItemCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opTypeItem", ignore = true)
    @Mapping(source = "insuranceCompanyId", target = "insuranceCompany.id")
    OpTypeItemInsurance toInsuranceEntity(OpTypeItemInsurances request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opTypeItem", ignore = true)
    @Mapping(source = "priceTypeId", target = "priceCategory.id")
    OpTypeItemPrice toPriceCategoryEntity(Prices request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "prices", ignore = true)
    @Mapping(target = "insurances", ignore = true)
    void updateOpTypeItem(@MappingTarget OpTypeItem entity, OpTypeItemUpdateRequest request);

    OpTypeItemReadResponse toReadDto(OpTypeItem entity);

    @Mapping(target = "insurances", ignore = true)
    @Mapping(target = "prices", ignore = true)
    OpTypeItemReadByIdResponse toReadByIdDto(OpTypeItem entity);

    OpTypeItemExcelResponse toExcelDto(OpTypeItem entity);

    default List<OpTypeItemPricesDto> mapPrices(List<OpTypeItemPrice> prices, List<PriceCategory> allCategories) {
        return allCategories.stream()
                .map(category -> prices.stream()
                        .filter(price -> price.getPriceCategory().getId().equals(category.getId()))
                        .findFirst()
                        .map(price -> new OpTypeItemPricesDto(category.getName(), category.getId(), price.getPrice()))
                        .orElse(new OpTypeItemPricesDto(category.getName(), category.getId(), null)))
                .collect(Collectors.toList());
    }
//
//    default List<OpTypeItemPrice> updateOpTypePrices(List<OpTypeItemPricesUpdateRequest> request, OpTypeItem opTypeItem) {
//        Map<Long, OpTypeItemPrice> currentPrices = opTypeItem.getPrices().stream()
//                .collect(Collectors.toMap(p -> p.getPriceCategory().getId(), p -> p));
//
//        return request.stream()
//                .filter(p -> p.getPrice() != null) // Null olmayanları götür
//                .map(priceDto ->
//                        currentPrices.containsKey(priceDto.getPriceCategoryId())
//                                ? updateExistingPrice(currentPrices.get(priceDto.getPriceCategoryId()), priceDto.getPrice(), opTypeItem)
//                                : createNewPrice(opTypeItem, priceDto.getPriceCategoryId(), priceDto.getPrice())
//                )
//                .collect(Collectors.toList());
//
//    }
//
//    default List<OpTypeItemInsurance> updateOpTypeInsurance(List<OpTypeItemInsuranceUpdateRequest> request, OpTypeItem opTypeItem) {
//        Map<Long, OpTypeItemInsurance> currentInsurances = opTypeItem.getInsurances().stream()
//                .collect(Collectors.toMap(p -> p.getInsuranceCompany().getId(), p -> p));
//
//        return request.stream()
//                .filter(p -> p.getAmount() != null) // Null olmayanları götür
//                .map(insuranceDto ->
//                        currentInsurances.containsKey(insuranceDto.getInsuranceCompanyId())
//                                ? updateExistingInsurance(currentInsurances.get(insuranceDto.getInsuranceCompanyId()), insuranceDto.getAmount(), opTypeItem)
//                                : createNewInsurance(opTypeItem, insuranceDto)
//                )
//                .collect(Collectors.toList());
//    }
//
//    private OpTypeItemPrice updateExistingPrice(OpTypeItemPrice existingPrice,
//                                                BigDecimal newPrice,
//                                                OpTypeItem opTypeItem) {
//        existingPrice.setPrice(newPrice);
//        existingPrice.setOpTypeItem(opTypeItem);
//        return existingPrice;
//    }
//
//    private OpTypeItemPrice createNewPrice(OpTypeItem opTypeItem, Long categoryId, BigDecimal price) {
//        PriceCategory category = PriceCategory.builder().id(categoryId).build();
//        return new OpTypeItemPrice(null, price, category, opTypeItem);
//    }
//
//    private OpTypeItemInsurance updateExistingInsurance(OpTypeItemInsurance existingInsurance,
//                                                        BigDecimal newAmount,
//                                                        OpTypeItem opTypeItem) {
//        existingInsurance.setAmount(newAmount);
//        existingInsurance.setOpTypeItem(opTypeItem);
//        return existingInsurance;
//    }
//
//    private OpTypeItemInsurance createNewInsurance(OpTypeItem opTypeItem,
//                                                   OpTypeItemInsuranceUpdateRequest request) {
//        InsuranceCompany company = InsuranceCompany.builder().id(request.getInsuranceCompanyId()).build();
//        return new OpTypeItemInsurance(null, request.getName(), request.getAmount(), opTypeItem, company);
//    }
}
