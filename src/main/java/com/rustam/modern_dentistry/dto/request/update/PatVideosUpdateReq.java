package com.rustam.modern_dentistry.dto.request.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatVideosUpdateReq {
    LocalDate date;
    String description;
}
