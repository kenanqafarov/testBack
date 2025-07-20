package com.rustam.modern_dentistry.dto.response.read;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PatAnamnesisReadRes {
    Long id;
    String anamnesisName;
    String anamnesisCategoryName;
    String addedByName;
    LocalDate addedDateTime;
    Long patientId;
}
