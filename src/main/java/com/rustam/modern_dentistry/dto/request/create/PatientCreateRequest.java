package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.PriceCategoryStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.SpecializationStatus;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientCreateRequest {
    @NotBlank(message = "Ad boş ola bilməz")
    @Size(min = 3, max = 20, message = "Ad 3-20 simvol arasında olmalıdır")
    String name;
    @NotBlank(message = "Soyad boş ola bilməz")
    @Size(min = 3, max = 20, message = "Soyad 3-20 simvol arasında olmalıdır")
    String surname;
    @NotBlank(message = "Ata adı boş ola bilməz")
    @Size(min = 3, max = 20, message = "Ata adı 3-20 simvol arasında olmalıdır")
    String patronymic;
    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "FIN kod yalnız böyük hərflər və rəqəmlərdən ibarət 7 simvol olmalıdır."
    )
    String finCode;
    @NotNull(message = "zəhmət olmasa cinsiyyəti daxil edin")
    GenderStatus genderStatus;
    LocalDate dateOfBirth;
    @NotNull(message = "zəhmət olmasa qiymət kategoriyasını daxil edin")
    PriceCategoryStatus priceCategoryStatus;
    SpecializationStatus specializationStatus;
    @NotNull(message = "zəhmət olmasa Həkimi seçin daxil edin")
    UUID doctor_id; //doctor_id -sine gore gedib tapacaq
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "Please enter your phone number in the format (000)-000-0000.")
    String phone;
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "Please enter your phone number in the format (000)-000-0000.")
    String workPhone;
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "Please enter your phone number in the format (000)-000-0000.")
    String homePhone;
    String homeAddress;
    String workAddress;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter a valid email address.")
    String email;
}
