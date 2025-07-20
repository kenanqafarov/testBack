package com.rustam.modern_dentistry.dto.response.read;

import java.util.List;

public record PageResponse<T>(
        int totalPages,
        long totalElements,
        List<T> data
) {
}

