package com.rustam.modern_dentistry.dao.repository.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dto.response.OrderProductStockProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderFromWarehouseRepository extends JpaRepository<OrderFromWarehouse, Long>, JpaSpecificationExecutor<OrderFromWarehouse> {

    // used quantity gelmelidir mehsul istifadesi ekranindan ve remainin quantity-dide ona gore formalasir
    @Query(value = """
        SELECT 
            c.category_name AS categoryName,
            p.product_name AS productName,
            p.product_no AS productCode,
            ofwp.quantity AS entryQuantity,
            we.used_quantity AS usedQuantity, 
            (we.quantity - we.used_quantity) AS remainingQuantity
        FROM 
            order_from_warehouse ofw
        JOIN 
            order_from_warehouse_product ofwp ON ofw.id = ofwp.order_from_warehouse_id
        JOIN 
            product p ON p.id = ofwp.product_id
        JOIN 
            category c ON c.id = p.category_id
        JOIN 
            warehouse_entry_product we ON we.product_id = p.id
        WHERE 
            (:roomName IS NULL OR ofw.room = :roomName)
            AND (:categoryName IS NULL OR LOWER(c.category_name) LIKE LOWER(CONCAT('%', :categoryName, '%')))
            AND (:productName IS NULL OR LOWER(p.product_name) LIKE LOWER(CONCAT('%', :productName, '%')))
            AND (:productNo IS NULL OR p.product_no = :productNo)
    """, nativeQuery = true)
    List<OrderProductStockProjection> searchOrderRoomStockProducts(@Param("roomName") String roomName,
                                                                   @Param("categoryName") String categoryName,
                                                                   @Param("productName") String productName,
                                                                   @Param("productNo") Long productNo);
}


