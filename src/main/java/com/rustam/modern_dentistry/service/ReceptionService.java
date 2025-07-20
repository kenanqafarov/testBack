package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import com.rustam.modern_dentistry.dao.entity.users.Reception;
import com.rustam.modern_dentistry.dao.repository.ReceptionRepository;
import com.rustam.modern_dentistry.exception.custom.UserNotFountException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ReceptionService {

    ReceptionRepository receptionRepository;

    public List<Reception> findAll(){
        return receptionRepository.findAll();
    }

    public Reception findById(UUID id) {
        return receptionRepository.findById(id)
                .orElseThrow(() -> new UserNotFountException("No such reception found."));
    }
}
