package com.rustam.modern_dentistry.dto.response.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientExcelResponse {
    @JsonProperty("ID")
    Long id;
    @JsonProperty("Adı")
    String name;
    @JsonProperty("Soyadı")
    String surname;
    @JsonProperty("Ata adı")
    String patronymic;
    @JsonProperty("Fin kodu")
    String finCode;
    @JsonProperty("Cinsiyyət")
    GenderStatus genderStatus;
    @JsonProperty("Doğum tarixi")
    LocalDate dateOfBirth;
    @JsonProperty("Mobil nömrə")
    String phone;
    @JsonProperty("İş nömrəsi")
    String workPhone;
    @JsonProperty("Ev nömrəsi")
    String homePhone;
    @JsonProperty("Adres")
    String homeAddress;
    @JsonProperty("İş adresi")
    String workAddress;
    @JsonProperty("Email")
    String email;
    @JsonProperty("Həkim adı")
    String doctorName;
    @JsonProperty("Status")
    Boolean isBlocked;
}
