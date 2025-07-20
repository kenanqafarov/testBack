package com.rustam.modern_dentistry.service.settings.implant;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.implant.Implant;
import com.rustam.modern_dentistry.dao.repository.settings.implant.ImplantRepository;
import com.rustam.modern_dentistry.dto.request.create.ImplantCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantStatusUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.ImplantReadResponse;
import com.rustam.modern_dentistry.dto.response.read.ImplantResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.implant.ImplantMapper;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.settings.implant.ImplantSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ImplantService {

    ImplantRepository implantRepository;
    ImplantMapper implantMapper;
    UtilService utilService;

    public ImplantResponse create(ImplantCreateRequest implantCreateRequest) {
        boolean existsImplantByImplantBrandName = existsImplantByImplantBrandName(implantCreateRequest.getImplantBrandName());
        if (existsImplantByImplantBrandName){
            throw new ExistsException("bu implant db-de movcuddur");
        }
        Implant implant = implantRepository.save(
                Implant.builder()
                        .implantBrandName(implantCreateRequest.getImplantBrandName())
                        .status(Status.ACTIVE)
                        .build()
        );
        return implantMapper.toDto(implant);
    }

    private boolean existsImplantByImplantBrandName(String implantBrandName) {
        return implantRepository.existsImplantByImplantBrandName(implantBrandName);
    }

    @Transactional
    public List<ImplantReadResponse> read() {
        List<Implant> implants = implantRepository.findAll();
        return implantMapper.toDtos(implants);
    }

    public List<ImplantReadResponse> search(ImplantSearchRequest implantSearchRequest) {
        return implantMapper.toDtos(
                implantRepository.findAll(ImplantSpecification.filterBy(implantSearchRequest))
        );
    }

    public ImplantResponse update(ImplantUpdateRequest implantUpdateRequest) {
        Implant implant = findById(implantUpdateRequest.getImplantId());
        utilService.updateFieldIfPresent(implantUpdateRequest.getImplantBrandName(),implant::setImplantBrandName);
        implantRepository.save(implant);
        return implantMapper.toDto(implant);
    }

    public Implant findById(Long implantId) {
        return implantRepository.findById(implantId)
                .orElseThrow(() -> new NotFoundException("bele bir implant tapilmadi"));
    }

    public Status statusUpdate(ImplantStatusUpdateRequest implantStatusUpdateRequest) {
        Implant implant = findById(implantStatusUpdateRequest.getImplantId());
        implant.setStatus(implantStatusUpdateRequest.getStatus());
        implantRepository.save(implant);
        return implant.getStatus();
    }

    public void delete(Long id) {
        Implant implant = findById(id);
        implantRepository.delete(implant);
    }
}
