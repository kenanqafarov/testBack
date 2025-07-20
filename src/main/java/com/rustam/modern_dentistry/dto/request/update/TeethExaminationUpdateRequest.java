package com.rustam.modern_dentistry.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeethExaminationUpdateRequest {
    @NotNull
    Long id;
    Long teethId;
    List<Long> examinationIds;
}
