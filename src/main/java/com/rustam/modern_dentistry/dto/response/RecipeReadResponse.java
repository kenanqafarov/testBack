package com.rustam.modern_dentistry.dto.response;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class RecipeReadResponse {
    Long id;
    String name;
    Status status;
}
