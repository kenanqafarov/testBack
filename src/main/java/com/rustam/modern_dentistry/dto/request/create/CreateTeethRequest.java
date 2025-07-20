package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.ToothLocation;
import com.rustam.modern_dentistry.dao.entity.enums.status.ToothType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTeethRequest {
    @NotNull
    Long toothNo;
    @NotNull
    ToothType toothType;
    @NotNull
    ToothLocation toothLocation;
}
