package com.rustam.modern_dentistry.dto.response.read;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletionFromWarehouseReadResponse {

    LocalDate date;

    LocalTime time;

    List<DeletionFromWarehouseProductResponse> deletionFromWarehouseProductResponses;

    Integer number;

    String description;
}
