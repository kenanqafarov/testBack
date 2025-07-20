package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TechnicianUpdateRequest {
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).{8,}$",
            message = "Şifrə minimum 8 simvol olmalı, böyük/kiçik hərf, rəqəm və xüsusi simvol içerməlidir."
    )
    String password;

    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String name;

    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String surname;

    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "FIN kod yalnız böyük hərflər və rəqəmlərdən ibarət 7 simvol olmalıdır."
    )
    String finCode;

    LocalDate dateOfBirth;

    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}", message = "Please enter your phone number in the format (000)-000-00-00.")
    String phone;

    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}", message = "Please enter your phone number in the format (000)-000-00-00.")
    String phone2;

    GenderStatus genderStatus;

    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String patronymic;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter a valid email address.")
    String email;

    String address;
    String homePhone;
}
