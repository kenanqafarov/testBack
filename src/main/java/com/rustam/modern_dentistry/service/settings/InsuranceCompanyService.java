package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import com.rustam.modern_dentistry.dao.repository.settings.InsuranceCompanyRepository;
import com.rustam.modern_dentistry.dto.request.create.InsuranceCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.ICSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.UpdateICRequest;
import com.rustam.modern_dentistry.dto.response.read.InsuranceReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.ICSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;
import static com.rustam.modern_dentistry.mapper.settings.InsuranceCompanyMapper.INSURANCE_COMPANY_MAPPER;

@Service
@RequiredArgsConstructor
public class InsuranceCompanyService {
    private final InsuranceCompanyRepository repository;

    public void create(InsuranceCreateRequest request) {
        var insurance = INSURANCE_COMPANY_MAPPER.toEntity(request);
        repository.save(insurance);
    }

    public PageResponse<InsuranceReadResponse> read(PageCriteria pageCriteria) {
        var insurances = repository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(insurances.getTotalPages(), insurances.getTotalElements(), getContent(insurances.getContent()));
    }


    public List<InsuranceReadResponse> readList() {
        return getContent(repository.findAll());
    }

    public InsuranceReadResponse readById(Long id) {
        var insurance = getInsuranceById(id);
        return INSURANCE_COMPANY_MAPPER.toReadDto(insurance);
    }

    public void update(Long id, UpdateICRequest request) {
        var insuranceCompany = getInsuranceById(id);
        INSURANCE_COMPANY_MAPPER.updateInsuranceCompany(insuranceCompany, request);
        repository.save(insuranceCompany);
    }

    public void updateStatus(Long id) {
        var insurance = getInsuranceById(id);
        var status = insurance.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        insurance.setStatus(status);
        repository.save(insurance);
    }

    public void delete(Long id) {
        var insurance = getInsuranceById(id);
        repository.delete(insurance);
    }

    public PageResponse<InsuranceReadResponse> search(ICSearchRequest request, PageCriteria pageCriteria) {
        Page<InsuranceCompany> response = repository.findAll(
                ICSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), getContent(response.getContent()));
    }

    public InputStreamResource exportReservationsToExcel() {
        List<InsuranceCompany> reservations = repository.findAll();
        var list = reservations.stream().map(INSURANCE_COMPANY_MAPPER::toReadDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, InsuranceReadResponse.class);
        return new InputStreamResource(excelFile);
    }

    public InsuranceCompany getInsuranceById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də sığorta şirkəti tapımadı: " + id)
        );
    }

    public List<InsuranceReadResponse> getContent(List<InsuranceCompany> insurances) {
        return insurances.stream().map(INSURANCE_COMPANY_MAPPER::toReadDto).toList();
    }
}
