package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OpTypeCreateRequest {
    @NotBlank(message = "Əməliyyat kateqoriyasının adını daxil edin.")
    String categoryName;

    Status status;
    boolean colorSelection;
    boolean implantSelection;
    List<OpTypeInsuranceRequest> insurances;
}
