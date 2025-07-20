package com.rustam.modern_dentistry.dto.response.read;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatVideosReadRes {
    Long id;
    LocalDate date;
    String description;
    Long patientId;
    String url;
}
