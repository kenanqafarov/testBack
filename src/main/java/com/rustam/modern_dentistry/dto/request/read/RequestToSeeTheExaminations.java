package com.rustam.modern_dentistry.dto.request.read;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestToSeeTheExaminations {

    LocalDate date;
}
