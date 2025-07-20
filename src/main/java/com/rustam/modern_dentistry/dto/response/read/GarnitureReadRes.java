package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarnitureReadRes {
    Long id;
    String name;
    Status status;
}
