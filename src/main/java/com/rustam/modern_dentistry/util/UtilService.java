package com.rustam.modern_dentistry.util;

import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dao.entity.users.Reception;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouseProduct;
import com.rustam.modern_dentistry.dao.repository.BaseUserRepository;
import com.rustam.modern_dentistry.dao.repository.DoctorRepository;
import com.rustam.modern_dentistry.dao.repository.PatientRepository;
import com.rustam.modern_dentistry.dao.repository.ReceptionRepository;
import com.rustam.modern_dentistry.dao.repository.warehouse_operations.OrderFromWarehouseRepository;
import com.rustam.modern_dentistry.dto.response.TokenPair;
import com.rustam.modern_dentistry.exception.custom.DoctorNotFoundException;
import com.rustam.modern_dentistry.exception.custom.InvalidUUIDFormatException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.exception.custom.UserNotFountException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UtilService {

    PatientRepository patientRepository;
    BaseUserRepository baseUserRepository;
    JwtUtil jwtUtil;
    OrderFromWarehouseRepository orderFromWarehouseRepository;

    public Patient findByPatientId(Long userId){
        return patientRepository.findById(userId)
                .orElseThrow(() -> new UserNotFountException("No such patient found."));
    }

    public UUID convertToUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid UUID format for ID: " + id, e);
        }
    }

    public BaseUser findByUsername(String username) {
        return baseUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such username found."));
    }

    public TokenPair tokenProvider(String id, UserDetails userDetails) {
        return userDetails.isEnabled() ?
                TokenPair.builder()
                        .accessToken(jwtUtil.createToken(String.valueOf(id)))
                        .refreshToken(jwtUtil.createRefreshToken(String.valueOf(id)))
                        .build()
                : new TokenPair();
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public BaseUser findByBaseUserId(String currentUserId) {
        return baseUserRepository.findById(UUID.fromString(currentUserId))
                .orElseThrow(() -> new UserNotFountException("No such user found."));
    }

    public BaseUser findByBaseUserIdWithPermissions(String userId){
        return baseUserRepository.findUserWithAllPermissions(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFountException("No such user found."));
    }

    public boolean existsByUsernameAndEmailAndFinCodeAndColorCode(String username, String email,String finCode,String colorCode) {
        return baseUserRepository.existsUserFully(username,email,finCode,colorCode);
    }

    public  <T> void updateFieldIfPresent(T newValue, Consumer<T> setter) {
        if (newValue != null && !(newValue instanceof String str && str.isBlank())) {
            setter.accept(newValue);
        }
    }

    public OrderFromWarehouse findByOrderFromWarehouseId(Long id){
        return orderFromWarehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such order from warehouse found."));
    }

    public String generateGroupId(){
        return UUID.randomUUID().toString();
    }

}
