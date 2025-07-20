package com.rustam.modern_dentistry.mapper.settings.product;

import com.rustam.modern_dentistry.dao.entity.settings.product.Product;
import com.rustam.modern_dentistry.dto.response.create.ProductResponse;
import com.rustam.modern_dentistry.dto.response.read.ProductReadResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ProductMapper {
    ProductReadResponse toDto(Product product);

    List<ProductReadResponse> toDtos(List<Product> products);
}
