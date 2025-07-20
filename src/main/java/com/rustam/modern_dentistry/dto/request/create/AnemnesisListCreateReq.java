package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnemnesisListCreateReq {
    @NotBlank(message = "Anamnezin adını daxil edin.")
    String name;
    Status status;
    Long anamnesisCategoryId;
}
