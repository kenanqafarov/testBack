package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OpTypeItemCreateRequest {
    Long opTypeId;
    String operationName;
    String operationCode;
    boolean showTechnic;
    Status status;
    List<Prices> prices;
    List<OpTypeItemInsurances> insurances;
}
