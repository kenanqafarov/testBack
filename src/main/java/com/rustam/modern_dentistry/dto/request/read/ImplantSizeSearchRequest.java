package com.rustam.modern_dentistry.dto.request.read;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImplantSizeSearchRequest {
    Double diameter;

    Double length;
}
