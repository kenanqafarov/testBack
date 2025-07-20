package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Ceramic;
import com.rustam.modern_dentistry.dao.repository.settings.CeramicRepository;
import com.rustam.modern_dentistry.dto.request.create.CeramicCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateCeramicReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.CeramicReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.CeramicMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.CeramicSpecification;
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
public class CeramicService {
    private final CeramicRepository ceramicRepository;
    private final CeramicMapper ceramicMapper;

    public void create(CeramicCreateReq request) {
        var entity = ceramicMapper.toEntity(request);
        ceramicRepository.save(entity);
    }

    public PageResponse<CeramicReadRes> read(PageCriteria pageCriteria) {
        var ceramics = ceramicRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(ceramics.getTotalPages(), ceramics.getTotalElements(), getContent(ceramics.getContent()));
    }

    public List<CeramicReadRes> readList() {
        return getContent(ceramicRepository.findAll());
    }

    public CeramicReadRes readById(Long id) {
        var entity = getCeramicById(id);
        return ceramicMapper.toReadDto(entity);
    }

    public void update(Long id, UpdateCeramicReq request) {
        var entity = getCeramicById(id);
        ceramicMapper.update(entity, request);
        ceramicRepository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getCeramicById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        ceramicRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = getCeramicById(id);
        ceramicRepository.delete(entity);
    }

    public PageResponse<CeramicReadRes> search(SearchByNameAndStatus request, PageCriteria pageCriteria) {
        Page<Ceramic> response = ceramicRepository.findAll(
                CeramicSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), getContent(response.getContent()));
    }

    public InputStreamResource exportToExcel() {
        List<Ceramic> reservations = ceramicRepository.findAll();
        var list = reservations.stream().map(ceramicMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, NameAndStatusExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private List<CeramicReadRes> getContent(List<Ceramic> ceramics) {
        return ceramics.stream().map(ceramicMapper::toReadDto).toList();
    }

    public Ceramic getCeramicById(Long id) {
        return ceramicRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də keramik tapımadı: " + id)
        );
    }
}
