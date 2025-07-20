package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dto.ModulePermission;
import com.rustam.modern_dentistry.dto.request.update.ModulePermissionUpdateRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionCreateRequest {
    String permissionName;
    List<ModulePermission> permissions;
}
