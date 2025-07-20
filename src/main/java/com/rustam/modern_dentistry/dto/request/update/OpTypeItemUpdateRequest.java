package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OpTypeItemUpdateRequest {
    Long id;
    String operationName;
    String operationCode;
    Status status;
    List<OpTypeItemPricesUpdateRequest> prices;
    List<OpTypeItemInsuranceUpdateRequest> insurances;
}
