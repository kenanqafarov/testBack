package com.rustam.modern_dentistry.dto.request.read;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class PatBlacklistSearchReq {
    String fullName;
    String finCode;
    String mobilePhone;
}
