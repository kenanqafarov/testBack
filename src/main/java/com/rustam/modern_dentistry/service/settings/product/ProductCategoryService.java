package com.rustam.modern_dentistry.service.settings.product;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dao.repository.settings.product.CategoryRepository;
import com.rustam.modern_dentistry.dto.request.create.ProductCategoryRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductCategorySearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductCategoryStatusUpdatedRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductCategoryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.ProductCategoryResponse;
import com.rustam.modern_dentistry.dto.response.read.ProductCategoryReadResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.product.CategoryMapper;
import com.rustam.modern_dentistry.util.specification.settings.ProductCategorySpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductCategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public ProductCategoryResponse create(ProductCategoryRequest productCategoryRequest) {
        Category category = Category.builder()
                .categoryName(productCategoryRequest.getCategoryName())
                .status(Status.ACTIVE)
                .build();
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such category found."));
    }

    public List<ProductCategoryReadResponse> read() {
        List<Category> categories = categoryRepository.findAllWithProducts();
        return categories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public ProductCategoryReadResponse readById(Long id) {
        Category category = categoryRepository.findByIdWithProducts(id)
                .orElseThrow(() -> new NotFoundException("No such category found."));
        return categoryMapper.toDto(category);
    }

    public ProductCategoryResponse update(ProductCategoryUpdateRequest productCategoryUpdateRequest) {
        Category category = findById(productCategoryUpdateRequest.getId());
        if (!productCategoryUpdateRequest.getCategoryName().isBlank()){
            category.setCategoryName(productCategoryUpdateRequest.getCategoryName());
        }
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    public List<ProductCategoryResponse> search(ProductCategorySearchRequest productCategorySearchRequest) {
        List<Category> categories = categoryRepository.findAll(ProductCategorySpecification.filterBy(productCategorySearchRequest));
        return categoryMapper.toDtos(categories);
    }

    public ProductCategoryResponse statusUpdated(ProductCategoryStatusUpdatedRequest productCategoryStatusUpdatedRequest) {
        Category category = findById(productCategoryStatusUpdatedRequest.getId());
        category.setStatus(productCategoryStatusUpdatedRequest.getStatus());
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }
}
