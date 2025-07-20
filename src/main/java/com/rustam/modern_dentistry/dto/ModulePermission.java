package com.rustam.modern_dentistry.dto;


import com.rustam.modern_dentistry.dao.entity.enums.PermissionAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModulePermission {
    String moduleUrl;
    Set<PermissionAction> actions;
}