package com.rustam.modern_dentistry.dto.response.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PatientBlacklistExcel {
    @JsonProperty("Pasiyent")
    String fullName;
    @JsonProperty("Fin Kodu")
    String finCode;
    @JsonProperty("Mobil nömrə")
    String mobilePhone;
    @JsonProperty("Tarix")
    LocalDate addedDate;
    @JsonProperty("Səbəb")
    String blacklistReason;
}
