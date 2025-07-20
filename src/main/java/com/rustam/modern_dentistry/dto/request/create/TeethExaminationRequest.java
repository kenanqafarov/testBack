package com.rustam.modern_dentistry.dto.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeethExaminationRequest {
    @NotNull
    Long teethId;
    @NotNull
    Long examinationId;
}
