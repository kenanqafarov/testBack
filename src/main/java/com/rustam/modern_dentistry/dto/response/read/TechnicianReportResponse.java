package com.rustam.modern_dentistry.dto.response.read;

import java.math.BigDecimal;
import java.util.UUID;

public record TechnicianReportResponse(
        UUID technicianId,
        String fullName,
        BigDecimal totalDebt,
        BigDecimal totalPaid,
        BigDecimal totalRemaining
) {
}
