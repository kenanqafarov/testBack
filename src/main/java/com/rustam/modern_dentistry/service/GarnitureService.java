package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.Garniture;
import com.rustam.modern_dentistry.dao.repository.GarnitureRepository;
import com.rustam.modern_dentistry.dto.request.create.GarnitureCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateGarnitureReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.GarnitureReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.GarnitureMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.GarnitureSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;


@Service
@RequiredArgsConstructor
public class GarnitureService {
    private final GarnitureRepository garnitureRepository;
    private final GarnitureMapper garnitureMapper;

    public void create(GarnitureCreateReq request) {
        var entity = garnitureMapper.toEntity(request);
        garnitureRepository.save(entity);
    }

    public PageResponse<GarnitureReadRes> read(PageCriteria pageCriteria) {
        var garnitures = garnitureRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(garnitures.getTotalPages(), garnitures.getTotalElements(), getContent(garnitures.getContent()));
    }

    public List<GarnitureReadRes> readList() {
        return getContent(garnitureRepository.findAll());
    }

    public GarnitureReadRes readById(Long id) {
        var entity = getGarnitureById(id);
        return garnitureMapper.toReadDto(entity);
    }

    public void update(Long id, UpdateGarnitureReq request) {
        var entity = getGarnitureById(id);
        garnitureMapper.update(entity, request);
        garnitureRepository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getGarnitureById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        garnitureRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = getGarnitureById(id);
        garnitureRepository.delete(entity);
    }

    public PageResponse<GarnitureReadRes> search(SearchByNameAndStatus request, PageCriteria pageCriteria) {
        Page<Garniture> response = garnitureRepository.findAll(
                GarnitureSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), getContent(response.getContent()));
    }

    public InputStreamResource exportToExcel() {
        List<Garniture> garnitures = garnitureRepository.findAll();
        var list = garnitures.stream().map(garnitureMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, NameAndStatusExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private List<GarnitureReadRes> getContent(List<Garniture> garnitures) {
        return garnitures.stream().map(garnitureMapper::toReadDto).toList();
    }

    public Garniture getGarnitureById(Long id) {
        return garnitureRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də garnitur tapılmadı: " + id)
        );
    }

}
