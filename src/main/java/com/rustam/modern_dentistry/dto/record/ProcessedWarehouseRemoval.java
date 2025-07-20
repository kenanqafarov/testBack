package com.rustam.modern_dentistry.dto.record;

import com.rustam.modern_dentistry.dto.OutOfTheWarehouseDto;

import java.util.List;

public record ProcessedWarehouseRemoval(String groupId, List<OutOfTheWarehouseDto> dtos) {
}
