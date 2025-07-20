package com.rustam.modern_dentistry.util.factory;

import com.rustam.modern_dentistry.dao.entity.settings.permission.Permission;
import com.rustam.modern_dentistry.dao.entity.users.Admin;
import com.rustam.modern_dentistry.dao.repository.AdminRepository;
import com.rustam.modern_dentistry.dto.request.create.AddWorkerCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.AddWorkerUpdateRequest;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.exception.custom.UserNotFountException;
import com.rustam.modern_dentistry.service.settings.PermissionService;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.factory.field_util.FieldSetter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminFactory implements UserRoleFactory {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilService utilService;
    private final PermissionService permissionService;

    @Override
    public void createUser(AddWorkerCreateRequest addWorkerCreateRequest) {
        boolean existsByUsernameAndEmailAndFinCodeAndColorCode = utilService.existsByUsernameAndEmailAndFinCodeAndColorCode(addWorkerCreateRequest.getUsername(), addWorkerCreateRequest.getEmail(),
                addWorkerCreateRequest.getFinCode(), null
        );
        if (existsByUsernameAndEmailAndFinCodeAndColorCode){
            throw new ExistsException("bu fieldlar database-de movcuddur");
        }

        Set<Permission> permissions = addWorkerCreateRequest.getPermissions().stream()
                .map(permissionService::findByName)
                .collect(Collectors.toSet());
        Admin admin = Admin.builder()
                .username(addWorkerCreateRequest.getUsername())
                .password(passwordEncoder.encode(addWorkerCreateRequest.getPassword()))
                .name(addWorkerCreateRequest.getName())
                .surname(addWorkerCreateRequest.getSurname())
                .patronymic(addWorkerCreateRequest.getPatronymic())
                .finCode(addWorkerCreateRequest.getFinCode())
                .genderStatus(addWorkerCreateRequest.getGenderStatus())
                .dateOfBirth(addWorkerCreateRequest.getDateOfBirth())
                .degree(addWorkerCreateRequest.getDegree())
                .phone(addWorkerCreateRequest.getPhone())
                .phone2(addWorkerCreateRequest.getPhone2())
                .phone3(addWorkerCreateRequest.getPhone3())
                .email(addWorkerCreateRequest.getEmail())
                .address(addWorkerCreateRequest.getAddress())
                .experience(addWorkerCreateRequest.getExperience())
                .permissions(permissions)
                .enabled(true)
                .build();
        adminRepository.save(admin);
    }

    @Override
    public String getPermissionName() {
        return "ADMIN";
    }

    @Override
    public void updateUser(AddWorkerUpdateRequest request) {
        boolean existsByUsernameAndEmailAndFinCodeAndColorCode = utilService.existsByUsernameAndEmailAndFinCodeAndColorCode(request.getUsername(), request.getEmail(),
                request.getFinCode(), null
        );
        if (existsByUsernameAndEmailAndFinCodeAndColorCode){
            throw new ExistsException("bu fieldlar database-de movcuddur");
        }
        Admin admin = adminRepository.findById(request.getId())
                .orElseThrow(() -> new UserNotFountException("No such Admin found."));

        FieldSetter.setIfNotBlank(request.getName(), admin::setName);
        FieldSetter.setIfNotBlank(request.getSurname(), admin::setSurname);
        FieldSetter.setIfNotBlank(request.getPatronymic(), admin::setPatronymic);
        FieldSetter.setIfNotBlank(request.getUsername(), admin::setUsername);
        FieldSetter.setIfNotBlank(request.getAddress(), admin::setAddress);
        FieldSetter.setIfNotNull(request.getExperience(), admin::setExperience);
        FieldSetter.setIfNotNull(request.getDateOfBirth(), admin::setDateOfBirth);
        FieldSetter.setIfNotBlank(request.getFinCode(), admin::setFinCode);
        FieldSetter.setIfNotBlank(request.getHomePhone(), admin::setHomePhone);
        FieldSetter.setIfNotNull(request.getGenderStatus(), admin::setGenderStatus);
        FieldSetter.setIfNotBlank(request.getEmail(), admin::setEmail);
        FieldSetter.setIfNotBlank(request.getDegree(), admin::setDegree);
        FieldSetter.setIfNotBlank(request.getPhone(), admin::setPhone);
        FieldSetter.setIfNotBlank(request.getPhone2(), admin::setPhone2);
        FieldSetter.setIfNotBlank(request.getPhone3(), admin::setPhone3);
        FieldSetter.setIfNotBlank(request.getPassword(),
                pass -> admin.setPassword(passwordEncoder.encode(pass)));

        admin.setEnabled(true);
        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            Set<Permission> newPermissions = request.getPermissions().stream()
                    .map(permissionService::findByName) // Convert String → Permission
                    .collect(Collectors.toSet());

            admin.getPermissions().clear();
            admin.getPermissions().addAll(newPermissions);
        }

        adminRepository.save(admin);
    }

    @Override
    public void deleteUser(UUID id) {
        adminRepository.deleteById(id);
    }

}
