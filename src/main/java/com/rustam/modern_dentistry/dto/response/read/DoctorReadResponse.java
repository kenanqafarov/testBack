package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dao.entity.enums.Role;
import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorReadResponse {
    UUID doctorId;
    String username;
    String password;
    String name;
    String surname;
    String patronymic;
    String finCode;
    String colorCode;
    boolean enabled;
    GenderStatus genderStatus;
    LocalDate dateOfBirth;
    String degree;
    String phone;
    String phone2;
    String homePhone;
    String email;
    String address;
    Integer experience;
    Set<Role> authorities;
}
