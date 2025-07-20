package com.rustam.modern_dentistry.dto.response.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameAndStatusExcelResponse {
    @JsonProperty("Adı")
    String name;
    @JsonProperty("Status")
    Status status;
}
