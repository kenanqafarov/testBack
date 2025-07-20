package com.rustam.modern_dentistry.dao.repository.laboratory;

import com.rustam.modern_dentistry.dao.entity.laboratory.DentalOrderPayment;
import com.rustam.modern_dentistry.dto.response.read.TechnicianReportResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DentalOrderPaymentRepository extends JpaRepository<DentalOrderPayment, Long> {

    @Query("""
    SELECT new com.rustam.modern_dentistry.dto.response.read.TechnicianReportResponse(
        t.id,
        CONCAT(t.name, ' ', t.surname),
        COALESCE((
            SELECT SUM(o.price) FROM DentalOrder o WHERE o.technician.id = t.id
        ), 0),
        COALESCE((
            SELECT SUM(p.amount) FROM DentalOrderPayment p WHERE p.technician.id = t.id
        ), 0),
        COALESCE((
            SELECT SUM(o.price) FROM DentalOrder o WHERE o.technician.id = t.id
        ), 0) -
        COALESCE((
            SELECT SUM(p.amount) FROM DentalOrderPayment p WHERE p.technician.id = t.id
        ), 0)
    )
    FROM Technician t
""")
    List<TechnicianReportResponse> getTechnicianReportOptimized();
}
