package com.rustam.modern_dentistry.dto.request.read;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @NotBlank(message = "The username column cannot be empty.")
    @Size(min = 3, max = 20, message = "İstifadəçi adı 3-20 simvol arasında olmalıdır")
    private String username;
    @NotBlank(message = "The password column cannot be empty.")
    private String password;
}
