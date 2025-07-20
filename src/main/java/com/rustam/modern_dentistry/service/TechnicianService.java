package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.users.Technician;
import com.rustam.modern_dentistry.dao.repository.TechnicianRepository;
import com.rustam.modern_dentistry.dto.request.create.TechnicianCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.TechnicianSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.TechnicianUpdateRequest;
import com.rustam.modern_dentistry.dto.response.excel.TechnicianExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.dto.response.read.TechnicianReadResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.TechnicianMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.TechnicianSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;

@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final PasswordEncoder passwordEncoder;
    private final TechnicianMapper technicianMapper;
    private final TechnicianRepository technicianRepository;

    public void create(TechnicianCreateRequest request) {
        checkIfUserAlreadyExist(request.getUsername());
        var entity = technicianMapper.toEntity(request);
        entity.setUsername(request.getUsername().toLowerCase());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setEnabled(true);
        technicianRepository.save(entity);
    }

    public PageResponse<TechnicianReadResponse> read(PageCriteria pageCriteria) {
        var technicians = technicianRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var technicianReadResponse = getContentResponse(technicians.getContent());
        return new PageResponse<>(technicians.getTotalPages(), technicians.getTotalElements(), technicianReadResponse);
    }

    public TechnicianReadResponse readById(UUID id) {
        var technician = getTechnicianById(id);
        return technicianMapper.toReadDto(technician);
    }

    public void update(UUID id, TechnicianUpdateRequest request) {
        var technician = getTechnicianById(id);
        technicianMapper.update(technician, request);
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            technician.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        technicianRepository.save(technician);
    }

    public void updateStatus(UUID id) {
        var technician = getTechnicianById(id);
        var status = technician.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        technician.setStatus(status);
        technicianRepository.save(technician);
    }

    public void delete(UUID id) {
        var technician = getTechnicianById(id);
        technicianRepository.delete(technician);
    }

    public PageResponse<TechnicianReadResponse> search(TechnicianSearchRequest request, PageCriteria pageCriteria) {
        Page<Technician> response = technicianRepository.findAll(
                TechnicianSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var technicianReadResponses = getContentResponse(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), technicianReadResponses);
    }

    public InputStreamResource exportReservationsToExcel() {
        List<Technician> technicians = technicianRepository.findAll();
        var list = technicians.stream().map(technicianMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, TechnicianExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private void checkIfUserAlreadyExist(String username) {
        var existsByUsername = technicianRepository.existsByUsername(username);
        if (existsByUsername)
            throw new ExistsException("Bu adda istifadəçi adı mövcuddur" + username);
    }


    private List<TechnicianReadResponse> getContentResponse(List<Technician> content) {
        return content.stream().map((technicianMapper::toReadDto)).toList();
    }

    public Technician getTechnicianById(UUID id) {
        return technicianRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də texnik tapımadı: " + id)
        );
    }
}
