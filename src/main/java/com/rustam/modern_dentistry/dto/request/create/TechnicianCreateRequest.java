package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.Role;
import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.*;

@Getter
@Setter
public class TechnicianCreateRequest {
    @NotBlank(message = VALIDATION_USERNAME)
    @Size(min = 3, max = 20, message = "İstifadəçi Adı 3-20 simvol arasında olmalıdır")
    String username;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).{8,}$",
            message = "Şifrə minimum 8 simvol olmalı, böyük/kiçik hərf, rəqəm və xüsusi simvol içerməlidir."
    )
    @NotBlank(message = VALIDATION_PASSWORD)
    String password;

    @NotBlank(message = VALIDATION_NAME)
    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String name;

    @NotBlank(message = VALIDATION_SURNAME)
    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String surname;

    @NotBlank(message = VALIDATION_FIN_CODE)
    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "FIN kod yalnız böyük hərflər və rəqəmlərdən ibarət 7 simvol olmalıdır."
    )
    String finCode;

    @NotNull(message = VALIDATION_DATE)
    LocalDate dateOfBirth;

    @NotBlank(message = VALIDATION_MOBILE)
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}", message = "Please enter your phone number in the format (000)-000-00-00.")
    String phone;

    @NotNull(message = VALIDATION_GENDER)
    GenderStatus genderStatus;

    @NotBlank(message = "VALIDATION_PATRONYMIC")
    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String patronymic;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter a valid email address.")
    String email;

    String phone2;
    String address;
    String homePhone;

    @NotNull(message = VALIDATION_ROLE)
    Set<Role> authorities;
}
