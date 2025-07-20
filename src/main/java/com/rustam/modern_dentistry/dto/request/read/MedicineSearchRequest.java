package com.rustam.modern_dentistry.dto.request.read;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineSearchRequest {
    String name;
    Status status;
    Long recipeId;
}
