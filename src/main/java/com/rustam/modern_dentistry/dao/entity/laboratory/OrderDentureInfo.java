package com.rustam.modern_dentistry.dao.entity.laboratory;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Embeddable
@FieldDefaults(level = PRIVATE)
public class OrderDentureInfo {
    String color;
    String garniture;
}
