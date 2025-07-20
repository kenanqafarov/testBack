package com.rustam.modern_dentistry.dto.response.read;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@FieldDefaults(level = PRIVATE)
public class DentalOrderTeethListResponse {
    Long id;
    Long toothNo;
    String toothType;
    String toothLocation;
}
