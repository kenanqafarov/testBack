package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Color;
import com.rustam.modern_dentistry.dao.repository.settings.ColorRepository;
import com.rustam.modern_dentistry.dto.request.create.ColorCreateReq;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateColorReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.ColorReadRes;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.ColorMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.ColorSpecification;
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
public class ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    public void create(ColorCreateReq request) {
        var entity = colorMapper.toEntity(request);
        colorRepository.save(entity);
    }

    public PageResponse<ColorReadRes> read(PageCriteria pageCriteria) {
        var colors = colorRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(colors.getTotalPages(), colors.getTotalElements(), getContent(colors.getContent()));
    }

    public List<ColorReadRes> readList() {
        return getContent(colorRepository.findAll());
    }

    public ColorReadRes readById(Long id) {
        var entity = getColorById(id);
        return colorMapper.toReadDto(entity);
    }

    public void update(Long id, UpdateColorReq request) {
        var entity = getColorById(id);
        colorMapper.update(entity, request);
        colorRepository.save(entity);
    }

    public void updateStatus(Long id) {
        var entity = getColorById(id);
        var status = entity.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        entity.setStatus(status);
        colorRepository.save(entity);
    }

    public void delete(Long id) {
        var entity = getColorById(id);
        colorRepository.delete(entity);
    }

    public PageResponse<ColorReadRes> search(SearchByNameAndStatus request, PageCriteria pageCriteria) {
        Page<Color> response = colorRepository.findAll(
                ColorSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), getContent(response.getContent()));
    }

    public InputStreamResource exportToExcel() {
        List<Color> colors = colorRepository.findAll();
        var list = colors.stream().map(colorMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, NameAndStatusExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private List<ColorReadRes> getContent(List<Color> colors) {
        return colors.stream().map(colorMapper::toReadDto).toList();
    }

    public Color getColorById(Long id) {
        return colorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də rəng tapımadı: " + id)
        );
    }
}
