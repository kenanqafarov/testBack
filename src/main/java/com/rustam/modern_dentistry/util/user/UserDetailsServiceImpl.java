package com.rustam.modern_dentistry.util.user;

import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.repository.BaseUserRepository;
import com.rustam.modern_dentistry.dao.repository.DoctorRepository;
import com.rustam.modern_dentistry.service.DoctorService;
import com.rustam.modern_dentistry.util.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BaseUserRepository baseUserRepository;
    private final UtilService utilService;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser baseUser = baseUserRepository.findUserWithAllPermissions(utilService.convertToUUID(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        String id = baseUser.getId();
        if (baseUser instanceof Doctor) {
            baseUser = doctorRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new UsernameNotFoundException("Doctor not found with ID: " + id));
        }
        return new CustomUserDetails(baseUser);
    }
}
