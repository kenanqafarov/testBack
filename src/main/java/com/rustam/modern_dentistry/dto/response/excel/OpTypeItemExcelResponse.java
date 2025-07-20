package com.rustam.modern_dentistry.dto.response.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OpTypeItemExcelResponse {
    @JsonProperty("Əməliyyatın adı")
    String operationName;
    @JsonProperty("Əməliyyatın kodu")
    String operationCode;
    @JsonProperty("Status")
    Status status;
}
