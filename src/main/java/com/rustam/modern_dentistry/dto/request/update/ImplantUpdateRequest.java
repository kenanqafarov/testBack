package com.rustam.modern_dentistry.dto.request.update;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImplantUpdateRequest {

    Long implantId;
    String implantBrandName;
}
