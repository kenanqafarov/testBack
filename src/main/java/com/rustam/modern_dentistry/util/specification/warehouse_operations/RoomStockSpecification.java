package com.rustam.modern_dentistry.util.specification.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dao.entity.settings.product.Product;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouseProduct;
import com.rustam.modern_dentistry.dto.request.read.RoomStockRequest;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class RoomStockSpecification {

    public static Specification<OrderFromWarehouseProduct> filterByRoomStock(RoomStockRequest request) {
        return (root, query, cb) -> {
            Join<OrderFromWarehouseProduct, OrderFromWarehouse> orderJoin = root.join("orderFromWarehouse", JoinType.INNER);
            Join<OrderFromWarehouseProduct, Product> productJoin = root.join("product", JoinType.INNER);
            Join<Product, Category> categoryJoin = productJoin.join("category", JoinType.INNER);

            Predicate predicate = cb.conjunction();

            if (request.getRoomName() != null) {
                predicate = cb.and(predicate, cb.equal(orderJoin.get("room"), request.getRoomName()));
            }

            if (request.getCategoryName() != null && !request.getCategoryName().isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(categoryJoin.get("name")), "%" + request.getCategoryName().toLowerCase() + "%"));
            }

            if (request.getProductName() != null && !request.getProductName().isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(productJoin.get("productName")), "%" + request.getProductName().toLowerCase() + "%"));
            }

            if (request.getProductNo() != null ) {
                predicate = cb.and(predicate, cb.like(cb.lower(productJoin.get("productCode")), "%" + request.getProductNo() + "%"));
            }

            return predicate;
        };
    }
}

