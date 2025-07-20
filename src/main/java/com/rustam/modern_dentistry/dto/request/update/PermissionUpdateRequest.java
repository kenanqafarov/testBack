package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dto.ModulePermission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionUpdateRequest {

    Long id;

    String permissionName;

    List<ModulePermissionUpdateRequest> permissions;
}
