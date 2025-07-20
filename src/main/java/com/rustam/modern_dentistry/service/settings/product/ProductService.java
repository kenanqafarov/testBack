package com.rustam.modern_dentistry.service.settings.product;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dao.entity.settings.product.Product;
import com.rustam.modern_dentistry.dao.repository.settings.product.ProductRepository;
import com.rustam.modern_dentistry.dto.request.create.ProductRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductUpdatedStatusRequest;
import com.rustam.modern_dentistry.dto.response.create.ProductResponse;
import com.rustam.modern_dentistry.dto.response.read.ProductReadResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.ModelMapperConfig;
import com.rustam.modern_dentistry.mapper.settings.product.ProductMapper;
import com.rustam.modern_dentistry.util.specification.settings.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    ProductCategoryService productCategoryService;
    ModelMapper modelMapper;
    ProductMapper productMapper;

    public ProductResponse create(ProductRequest productRequest) {
        Category category = productCategoryService.findById(productRequest.getCategoryId());
        boolean existsByProductName = productRepository.existsByProductName(productRequest.getProductName());
        if (existsByProductName){
            throw new ExistsException("This product is usually already available.");
        }
        //BigDecimal sum = productRequest.getPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity()));
        Product product = Product.builder()
                .category(category)
                .productNo(productRequest.getProductNo())
                .productTitle(productRequest.getProductTitle())
                .productName(productRequest.getProductName())
                .status(Status.ACTIVE)
                .build();
        productRepository.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    public List<ProductResponse> read() {
        return productRepository.findAllWithCategory();
    }

    @Transactional
    public ProductResponse update(ProductUpdateRequest productUpdateRequest) {
        Product product = findById(productUpdateRequest.getId());
        //long quantitySum = product.getQuantity();
        if (productUpdateRequest.getProductName() != null && !productUpdateRequest.getProductName().isBlank()){
            product.setProductName(productUpdateRequest.getProductName());
        }
        if (productUpdateRequest.getProductNo() != null && productUpdateRequest.getProductNo() != 0){
            product.setProductNo(productUpdateRequest.getProductNo());
        }
        if (productUpdateRequest.getProductTitle()!= null && !productUpdateRequest.getProductTitle().isBlank()){
            product.setProductTitle(productUpdateRequest.getProductTitle());
        }
//        if (productUpdateRequest.getQuantity() != null && productUpdateRequest.getQuantity() != 0 ){
//            quantitySum += productUpdateRequest.getQuantity();
//            product.setQuantity(quantitySum);
//            BigDecimal resultValue = product.getPrice().multiply(BigDecimal.valueOf(quantitySum));
//            product.setSumPrice(resultValue);
//        }
//        if (productUpdateRequest.getPrice() != null && !productUpdateRequest.getPrice().equals(BigDecimal.ZERO)){
//            BigDecimal sum = productUpdateRequest.getPrice().multiply(BigDecimal.valueOf(quantitySum));
//            product.setPrice(productUpdateRequest.getPrice());
//            product.setSumPrice(sum);
//        }
        productRepository.save(product);
        return ProductResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getCategoryName())
                .productName(product.getProductName())
                .productNo(product.getProductNo())
                .productTitle(product.getProductTitle())
                .build();
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such product found."));
    }

    public ProductResponse readById(Long id) {
        return productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new NotFoundException("No such product found."));
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such product found."));
        productRepository.delete(product);
    }

    public Product findByIdAndCategoryId(Long productId,Long categoryId) {
        return productRepository.findByIdAndCategoryId(productId,categoryId)
                .orElseThrow(() -> new NotFoundException("No such product found."));
    }

    public ProductReadResponse statusUpdated(ProductUpdatedStatusRequest productUpdatedStatusRequest) {
        Product product = findById(productUpdatedStatusRequest.getProductId());
        product.setStatus(productUpdatedStatusRequest.getStatus());
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public List<ProductReadResponse> search(ProductSearchRequest productSearchRequest) {
        List<Product> products = productRepository.findAll(ProductSpecification.filterBy(productSearchRequest));
        return productMapper.toDtos(products);
    }
}
