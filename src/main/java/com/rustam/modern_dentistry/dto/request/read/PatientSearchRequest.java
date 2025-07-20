package com.rustam.modern_dentistry.dto.request.read;

import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientSearchRequest {
    String name;
    String surname;
    String fullName;
    String finCode;
    GenderStatus genderStatus;
    String phone;
}
