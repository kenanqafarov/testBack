package com.rustam.modern_dentistry.service.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisCategory;
import com.rustam.modern_dentistry.dao.repository.settings.anemnesis.AnemnesisCategoryRepository;
import com.rustam.modern_dentistry.dto.request.create.AnemnesisCatCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.AnemnesisCatSearchReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateAnemnesisCatReq;
import com.rustam.modern_dentistry.dto.response.excel.AnamnesisExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.AnamnesisCatReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.anemnesis.AnemnesisCatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;
import static com.rustam.modern_dentistry.mapper.settings.anemnesis.AnemnesisCategoryMapper.ANAMNESIS_CAT_MAPPER;

@Service
@RequiredArgsConstructor
public class AnemnesisCategoryService {
    private final AnemnesisCategoryRepository repository;

    public void create(AnemnesisCatCreateReq request) {
        var entity = ANAMNESIS_CAT_MAPPER.toEntity(request);
        repository.save(entity);
    }

    public PageResponse<AnamnesisCatReadResponse> read(PageCriteria pageCriteria) {
        var anemnesis = repository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(anemnesis.getTotalPages(), anemnesis.getTotalElements(), getContent(anemnesis.getContent()));
    }

    public List<AnamnesisCatReadResponse> readList() {
        return getContent(repository.findAll());
    }

    public AnamnesisCatReadResponse readById(Long id) {
        var entity = getAnemnesisCatById(id);
        return ANAMNESIS_CAT_MAPPER.toReadDto(entity);
    }

    public void update(Long id, UpdateAnemnesisCatReq request) {
        var entity = getAnemnesisCatById(id);
        ANAMNESIS_CAT_MAPPER.updateAnemnesisCategory(entity, request);
        repository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getAnemnesisCatById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        repository.save(entity);
    }

    public void delete(Long id) {
        var anamnesisCategory = getAnemnesisCatById(id);
        repository.delete(anamnesisCategory);
    }

    public PageResponse<AnamnesisCatReadResponse> search(AnemnesisCatSearchReq request, PageCriteria pageCriteria) {
        Page<AnamnesisCategory> response = repository.findAll(
                AnemnesisCatSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), getContent(response.getContent()));
    }

    public InputStreamResource exportReservationsToExcel() {
        List<AnamnesisCategory> reservations = repository.findAll();
        var list = reservations.stream().map(ANAMNESIS_CAT_MAPPER::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, AnamnesisExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    protected AnamnesisCategory getAnemnesisCatById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də anemnez tapımadı: " + id)
        );
    }

    private List<AnamnesisCatReadResponse> getContent(List<AnamnesisCategory> anamnesisCategories) {
        return anamnesisCategories.stream().map(ANAMNESIS_CAT_MAPPER::toReadDto).toList();
    }
}
