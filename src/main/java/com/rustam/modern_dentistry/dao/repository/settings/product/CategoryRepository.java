package com.rustam.modern_dentistry.dao.repository.settings.product;

import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dto.response.read.ProductCategoryReadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {

    @Query("""
    SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products
""")
    List<Category> findAllWithProducts();

    @Query("""
    SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id
""")
    Optional<Category> findByIdWithProducts(Long id);
}
