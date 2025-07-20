package com.rustam.modern_dentistry.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnemnesisListReq {
    @NotBlank(message = "Anamnezin adını daxil edin.")
    String name;
}
