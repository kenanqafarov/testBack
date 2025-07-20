package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.users.Admin;
import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import com.rustam.modern_dentistry.dao.repository.AdminRepository;
import com.rustam.modern_dentistry.exception.custom.UserNotFountException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AdminService {

    AdminRepository adminRepository;

    public Admin findById(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFountException("No such admin found."));
    }
}
