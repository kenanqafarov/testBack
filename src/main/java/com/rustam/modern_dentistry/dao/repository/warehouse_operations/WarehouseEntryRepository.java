package com.rustam.modern_dentistry.dao.repository.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseEntry;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReadProjection;
import com.rustam.modern_dentistry.dto.response.read.WarehouseReadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseEntryRepository extends JpaRepository<WarehouseEntry,Long>, JpaSpecificationExecutor<WarehouseEntry> {
    @Query(value = """
    SELECT
        wep.warehouse_entry_id AS id,
        c.category_name AS categoryName,
        p.product_name AS productName,
        p.product_no AS productNo,
        SUM(wep.quantity) AS sumQuantity
    FROM
        warehouse_entry_product wep
    JOIN
        product p ON wep.product_id = p.id
    JOIN
        category c ON p.category_id = c.id
    GROUP BY
        wep.warehouse_entry_id, c.category_name, p.product_name, p.product_no
""", nativeQuery = true)
    List<WarehouseReadProjection> readWarehouse();

    @Query(value = """
    SELECT
        wep.warehouse_entry_id AS id,
        c.category_name AS categoryName,
        p.product_name AS productName,
        p.product_no AS productNo,
        SUM(wep.quantity) AS sumQuantity
    FROM
        warehouse_entry_product wep
    JOIN
        product p ON wep.product_id = p.id
    JOIN
        category c ON p.category_id = c.id
    WHERE
        (:categoryId IS NULL OR c.id = :categoryId)
        AND (:productName IS NULL OR LOWER(p.product_name) LIKE LOWER(CONCAT('%', :productName, '%')))
        AND (:productNo IS NULL OR p.product_no = :productNo)
    GROUP BY
        wep.warehouse_entry_id, c.category_name, p.product_name, p.product_no
""", nativeQuery = true)
    List<WarehouseReadProjection> readWarehouse( Long categoryId,
                                                 String productName,
                                                 Long productNo);


}
