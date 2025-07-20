package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.settings.BlacklistResult;
import com.rustam.modern_dentistry.dao.repository.settings.BlacklistResultRepository;
import com.rustam.modern_dentistry.dto.request.UpdateBlacklistResultReq;
import com.rustam.modern_dentistry.dto.request.create.BlacklistResultCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.BlacklistResultSearchReq;
import com.rustam.modern_dentistry.dto.response.excel.BlacklistResultExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.BlacklistResultReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.BlackListResultMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.BlacklistResultSpecification;
import jakarta.validation.Valid;
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
public class BlacklistResultService {
    private final BlackListResultMapper blackListResultMapper;
    private final BlacklistResultRepository blacklistResultRepository;

    public void create(@Valid BlacklistResultCreateReq request) {
        var existsByStatusName = blacklistResultRepository.existsByStatusNameIgnoreCase(request.getStatusName());
        if (existsByStatusName) throw new ExistsException("Bu adda status artıq mövcuddur");
        var entity = blackListResultMapper.toEntity(request);
        blacklistResultRepository.save(entity);
    }

    public PageResponse<BlacklistResultReadRes> read(PageCriteria pageCriteria) {
        var anemnesis = blacklistResultRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(anemnesis.getTotalPages(), anemnesis.getTotalElements(), getContent(anemnesis.getContent()));
    }

    public List<BlacklistResultReadRes> readList() {
        return getContent(blacklistResultRepository.findAll());

    }

    public BlacklistResultReadRes readById(Long id) {
        var entity = getBlackListById(id);
        return blackListResultMapper.toReadDto(entity);
    }

    public void update(Long id, UpdateBlacklistResultReq request) {
        var entity = getBlackListById(id);
        blackListResultMapper.update(entity, request);
        blacklistResultRepository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getBlackListById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        blacklistResultRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = getBlackListById(id);
        blacklistResultRepository.delete(entity);
    }

    public PageResponse<BlacklistResultReadRes> search(BlacklistResultSearchReq request, PageCriteria pageCriteria) {
        Page<BlacklistResult> response = blacklistResultRepository.findAll(
                BlacklistResultSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var blacklistResults = getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), blacklistResults);
    }

    public InputStreamResource exportReservationsToExcel() {
        List<BlacklistResult> blacklistResults = blacklistResultRepository.findAll();
        var list = blacklistResults.stream().map(blackListResultMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, BlacklistResultExcelResponse.class);
        return new InputStreamResource(excelFile);
    }


    public BlacklistResult getBlackListById(Long id) {
        return blacklistResultRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də qara siyahı tapımadı: " + id)
        );
    }

    private List<BlacklistResultReadRes> getContent(List<BlacklistResult> blacklistResults) {
        return blacklistResults.stream().map(blackListResultMapper::toReadDto).toList();
    }
}
