package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Metal;
import com.rustam.modern_dentistry.dao.repository.settings.MetalRepository;
import com.rustam.modern_dentistry.dto.request.create.MetalCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateMetalReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.MetalReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.MetalMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.MetalSpecification;
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
public class MetalService {
    private final MetalRepository metalRepository;
    private final MetalMapper metalMapper;

    public void create(MetalCreateReq request) {
        var entity = metalMapper.toEntity(request);
        metalRepository.save(entity);
    }

    public PageResponse<MetalReadRes> read(PageCriteria pageCriteria) {
        var metals = metalRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(metals.getTotalPages(), metals.getTotalElements(), getContent(metals.getContent()));
    }

    public List<MetalReadRes> readList() {
        return getContent(metalRepository.findAll());
    }

    public MetalReadRes readById(Long id) {
        var entity = getMetalById(id);
        return metalMapper.toReadDto(entity);
    }

    public void update(Long id, UpdateMetalReq request) {
        var entity = getMetalById(id);
        metalMapper.update(entity, request);
        metalRepository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getMetalById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        metalRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = getMetalById(id);
        metalRepository.delete(entity);
    }

    public PageResponse<MetalReadRes> search(SearchByNameAndStatus request, PageCriteria pageCriteria) {
        Page<Metal> response = metalRepository.findAll(
                MetalSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), getContent(response.getContent()));
    }

    public InputStreamResource exportToExcel() {
        List<Metal> reservations = metalRepository.findAll();
        var list = reservations.stream().map(metalMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, NameAndStatusExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private List<MetalReadRes> getContent(List<Metal> metals) {
        return metals.stream().map(metalMapper::toReadDto).toList();
    }

    public Metal getMetalById(Long id) {
        return metalRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də metal tapımadı: " + id)
        );
    }
}
