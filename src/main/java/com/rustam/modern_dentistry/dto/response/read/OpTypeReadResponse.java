package com.rustam.modern_dentistry.dto.response.read;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class OpTypeReadResponse {
    Long id;
    String categoryName;
    boolean colorSelection;
    boolean implantSelection;
    Status status;
    Long opTypeItemCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<OpInsuranceReadResponse> insurances;
}
