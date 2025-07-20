package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.settings.PriceCategory;
import com.rustam.modern_dentistry.dao.repository.settings.PriceCategoryRepository;
import com.rustam.modern_dentistry.dto.request.create.PriceCategoryCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.PriceCategorySearchRequest;
import com.rustam.modern_dentistry.dto.request.update.PriceCategoryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.PriceCategoryReadResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.PriceCategorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;
import static com.rustam.modern_dentistry.mapper.settings.PriceCategoryMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class PriceCategoryService {
    private final PriceCategoryRepository priceCategoryRepository;

    public void create(PriceCategoryCreateRequest request) {
        var priceCategory = MAPPER.toEntity(request);
        priceCategoryRepository.save(priceCategory);
    }

    public List<PriceCategoryReadResponse> read() {
        return priceCategoryRepository.findAll().stream().map(MAPPER::toDto).toList();
    }

    public PriceCategoryReadResponse readById(Long id) {
        var priceCategory = getPriceCategoryById(id);
        return MAPPER.toDto(priceCategory);
    }

    public void update(Long id, PriceCategoryUpdateRequest request) {
        var priceCategory = getPriceCategoryById(id);
        MAPPER.updateEntity(priceCategory, request);
        priceCategoryRepository.save(priceCategory);
    }

    public void updateStatus(Long id) {
        var priceCategory = getPriceCategoryById(id);
        var status = priceCategory.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        priceCategory.setStatus(status);
        priceCategoryRepository.save(priceCategory);
    }

    public void delete(Long id) {
        var priceCategory = getPriceCategoryById(id);
        priceCategoryRepository.delete(priceCategory);
    }

    public List<PriceCategoryReadResponse> search(PriceCategorySearchRequest request) {
        return priceCategoryRepository
                .findAll(PriceCategorySpecification.filterBy(request))
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    public InputStreamResource exportReservationsToExcel() {
        List<PriceCategory> priceCategories = priceCategoryRepository.findAll();
        var list = priceCategories.stream().map(MAPPER::toDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, PriceCategoryReadResponse.class);
        return new InputStreamResource(excelFile);
    }

    private PriceCategory getPriceCategoryById(Long id) {
        return priceCategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Price Category with id %s not found", id))
        );
    }
}
