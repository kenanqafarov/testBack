package com.rustam.modern_dentistry.util.factory;

import com.rustam.modern_dentistry.dao.entity.settings.permission.Permission;
import com.rustam.modern_dentistry.dto.request.create.AddWorkerCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.AddWorkerUpdateRequest;

import java.util.UUID;

public interface UserRoleFactory {
    void createUser(AddWorkerCreateRequest addWorkerCreateRequest);

    String getPermissionName();

    void updateUser(AddWorkerUpdateRequest addWorkerUpdateRequest);

    void deleteUser(UUID id);
}
