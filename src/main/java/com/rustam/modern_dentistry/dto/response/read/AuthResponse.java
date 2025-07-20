package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dto.response.TokenPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private TokenPair tokenPair;
}
