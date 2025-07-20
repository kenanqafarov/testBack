package com.rustam.modern_dentistry.dto.response.read;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@FieldDefaults(level = PRIVATE)
public class PatBlacklistReadRes {
    Long id;
    String fullName;
    String finCode;
    String mobilePhone;
    LocalDate addedDate;
    String blacklistReason;
}
