package com.rustam.modern_dentistry.dto.response.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
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
public class PermissionCreateResponse {

    String permissionName;
    List<ModulePermissionUpdateRequest> modulePermissions;
}
