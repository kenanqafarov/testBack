package com.rustam.modern_dentistry.dto.response.excel;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnamnesisExcelResponse {
    String name;
    Status status;
}
