package com.rustam.modern_dentistry.service.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisList;
import com.rustam.modern_dentistry.dao.repository.settings.anemnesis.AnemnesisListRepository;
import com.rustam.modern_dentistry.dto.request.create.AnemnesisListCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.AnemnesisListSearchReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateAnemnesisListReq;
import com.rustam.modern_dentistry.dto.response.read.AnemnesisListReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.anemnesis.AnemnesisListSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.PASSIVE;
import static com.rustam.modern_dentistry.mapper.settings.anemnesis.AnemnesisListMapper.ANAMNESIS_LIST_MAPPER;

@Service
@RequiredArgsConstructor
public class AnemnesisListService {
    private final AnemnesisListRepository repository;
    private final AnemnesisCategoryService anemnesisCategoryService;

    public void create(AnemnesisListCreateReq request) {
        var entity = ANAMNESIS_LIST_MAPPER.toEntity(request);
        var anamnesisCategory = anemnesisCategoryService.getAnemnesisCatById(request.getAnamnesisCategoryId());
        entity.setAnamnesisCategory(anamnesisCategory);
        repository.save(entity);
    }

    public PageResponse<AnemnesisListReadResponse> read(PageCriteria pageCriteria) {
        var anemnesis = repository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var content = getContent(anemnesis.getContent());
        return new PageResponse<>(anemnesis.getTotalPages(), anemnesis.getTotalElements(), content);
    }

    public List<AnemnesisListReadResponse> readList() {
        return getContent(repository.findAll());
    }

    private List<AnemnesisListReadResponse> getContent(List<AnamnesisList> anamnesisLists) {
        return anamnesisLists.stream().map(ANAMNESIS_LIST_MAPPER::toReadDto).toList();
    }


    public AnemnesisListReadResponse readById(Long id) {
        var entity = getAnemnesisListById(id);
        return ANAMNESIS_LIST_MAPPER.toReadDto(entity);
    }

    public void update(Long id, UpdateAnemnesisListReq request) {
        var entity = getAnemnesisListById(id);
        ANAMNESIS_LIST_MAPPER.updateAnemnesisList(entity, request);
        repository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getAnemnesisListById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        repository.save(entity);
    }

    public void delete(Long id) {
        var anamnesis = getAnemnesisListById(id);
        repository.delete(anamnesis);
    }

    public PageResponse<AnamnesisList> search(AnemnesisListSearchReq request, PageCriteria pageCriteria) {
        Page<AnamnesisList> response = repository.findAll(
                AnemnesisListSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), response.getContent());
    }

    public InputStreamResource exportReservationsToExcel() {
        List<AnamnesisList> reservations = repository.findAll();
        var list = reservations.stream().map(ANAMNESIS_LIST_MAPPER::toReadDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, AnemnesisListReadResponse.class);
        return new InputStreamResource(excelFile);
    }

    public AnamnesisList getAnemnesisListById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də anemnez tapımadı: " + id)
        );
    }
}
