package com.rustam.modern_dentistry.dto.request.create;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatXrayCreateReq {
    LocalDate date;
    String description;
    Long patientId;
}
