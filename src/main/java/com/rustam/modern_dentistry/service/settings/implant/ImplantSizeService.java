package com.rustam.modern_dentistry.service.settings.implant;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.implant.Implant;
import com.rustam.modern_dentistry.dao.entity.settings.implant.ImplantSizes;
import com.rustam.modern_dentistry.dao.repository.settings.implant.ImplantSizeRepository;
import com.rustam.modern_dentistry.dto.request.create.ImplantSizeCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSizeSearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSizeSearchStatusRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantSizeStatusUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantSizeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.ImplantSizeResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.implant.ImplantSizeMapper;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.settings.implant.ImplantSizeSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ImplantSizeService {

    ImplantSizeRepository implantSizeRepository;
    ImplantService implantService;
    ImplantSizeMapper implantSizeMapper;
    UtilService utilService;

    public ImplantSizeResponse create(ImplantSizeCreateRequest implantSizeCreateRequest) {
        ImplantSizes implantSizes = ImplantSizes.builder()
                .implant(implantService.findById(implantSizeCreateRequest.getImplantSizeId()))
                .length(implantSizeCreateRequest.getLength())
                .diameter(implantSizeCreateRequest.getDiameter())
                .status(Status.ACTIVE)
                .build();
        return implantSizeMapper.toDto(implantSizes);
    }

    public List<ImplantSizeResponse> read() {
        return implantSizeMapper.toDtos(implantSizeRepository.findAll());
    }

    public List<ImplantSizeResponse> searchStatus(ImplantSizeSearchStatusRequest implantSizeSearchRequest) {
        return implantSizeMapper.toDtos(
                implantSizeRepository.findAll(
                        ImplantSizeSpecification.filterByStatus(implantSizeSearchRequest)
                )
        );
    }

    public List<ImplantSizeResponse> search(ImplantSizeSearchRequest implantSizeSearchRequest) {
        return implantSizeMapper.toDtos(
                implantSizeRepository.findAll(
                        ImplantSizeSpecification.filterBy(implantSizeSearchRequest)
                )
        );
    }

    public Status statusUpdated(ImplantSizeStatusUpdateRequest request) {
        ImplantSizes implantSizes = findById(request.getImplantSizeId());
        implantSizes.setStatus(request.getStatus());
        implantSizeRepository.save(implantSizes);
        return implantSizes.getStatus();
    }

    private ImplantSizes findById(Long implantSizeId) {
        return implantSizeRepository.findById(implantSizeId)
                .orElseThrow(() -> new NotFoundException("bele bir implant size tapilmadi"));
    }

    public ImplantSizeResponse update(ImplantSizeUpdateRequest implantSizeUpdateRequest) {
        ImplantSizes implantSizes = findById(implantSizeUpdateRequest.getImplantSizeId());
        utilService.updateFieldIfPresent(implantSizeUpdateRequest.getLength(),implantSizes::setLength);
        utilService.updateFieldIfPresent(implantSizeUpdateRequest.getDiameter(),implantSizes::setDiameter);
        implantSizeRepository.save(implantSizes);
        return implantSizeMapper.toDto(implantSizes);
    }

    public void delete(Long id) {
        ImplantSizes implantSizes = findById(id);
        implantSizeRepository.delete(implantSizes);
    }
}
