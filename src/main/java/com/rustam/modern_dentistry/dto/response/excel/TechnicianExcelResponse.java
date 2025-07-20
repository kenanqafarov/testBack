package com.rustam.modern_dentistry.dto.response.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;

import java.time.LocalDate;

public class TechnicianExcelResponse {
    @JsonProperty("İstifadəçi adı")
    String username;
    @JsonProperty("Ad")
    String name;
    @JsonProperty("Soyad")
    String surname;
    @JsonProperty("Ata adı")
    String patronymic;
    @JsonProperty("Doğum tarixi")
    LocalDate birthDate;
    @JsonProperty("Mobil nömrə 1")
    String phone;
    @JsonProperty("Mobil nömrə 2")
    String phone2;
    @JsonProperty("Ev Telefon")
    String homePhone;
    @JsonProperty("Ünvan")
    String address;
    @JsonProperty("Fin Kod")
    String finCode;
    @JsonProperty("Cinsiyyət")
    GenderStatus genderStatus;
    @JsonProperty("Status")
    Status status;
}
