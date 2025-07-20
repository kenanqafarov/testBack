package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.Cabinet;
import com.rustam.modern_dentistry.dao.repository.settings.CabinetRepository;
import com.rustam.modern_dentistry.dto.request.create.CabinetCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.CabinetSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.CabinetStatusUpdatedRequest;
import com.rustam.modern_dentistry.dto.request.update.CabinetUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.CabinetResponse;
import com.rustam.modern_dentistry.dto.response.update.CabinetStatusUpdatedResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.CabinetMapper;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.settings.CabinetSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CabinetService {

    CabinetRepository cabinetRepository;
    CabinetMapper cabinetMapper;
    UtilService utilService;

    public CabinetResponse create(CabinetCreateRequest cabinetCreateRequest) {
        boolean existsCabinetByCabinetName = existsCabinetByCabinetName(cabinetCreateRequest.getCabinetName());
        if (existsCabinetByCabinetName){
            throw new ExistsException("bele bir kabinet db-de movcuddur");
        }
        Cabinet cabinet = cabinetRepository.save(
                Cabinet.builder()
                        .cabinetName(cabinetCreateRequest.getCabinetName())
                        .status(Status.ACTIVE)
                        .build()
        );
        return cabinetMapper.toDto(cabinet);
    }

    private boolean existsCabinetByCabinetName(String cabinetName) {
        return cabinetRepository.existsCabinetByCabinetName(cabinetName);
    }

    public List<CabinetResponse> read() {
        List<Cabinet> cabinets = cabinetRepository.findAll();
        return cabinetMapper.toDtos(cabinets);
    }

    public List<CabinetResponse> search(CabinetSearchRequest cabinetSearchRequest){
        List<Cabinet> cabinets = cabinetRepository.findAll(CabinetSpecification.filterBy(cabinetSearchRequest));
        return cabinetMapper.toDtos(cabinets);
    }

    public CabinetResponse update(CabinetUpdateRequest cabinetUpdateRequest) {
        Cabinet cabinet = findById(cabinetUpdateRequest.getId());
        utilService.updateFieldIfPresent(cabinetUpdateRequest.getCabinetName(),cabinet::setCabinetName);
        cabinetRepository.save(cabinet);
        return CabinetResponse.builder()
                .id(cabinet.getId())
                .cabinetName(cabinet.getCabinetName())
                .status(cabinet.getStatus())
                .build();
    }

    private Cabinet findById(Long id) {
        return cabinetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("bele bir otaq tapilmadi"));
    }

    public CabinetStatusUpdatedResponse statusUpdated(CabinetStatusUpdatedRequest cabinetStatusUpdatedRequest) {
        Cabinet cabinet = findById(cabinetStatusUpdatedRequest.getId());
        cabinet.setStatus(cabinetStatusUpdatedRequest.getStatus());
        cabinetRepository.save(cabinet);
        return CabinetStatusUpdatedResponse.builder().status(cabinetStatusUpdatedRequest.getStatus()).build();
    }

    public void delete(Long id) {
        Cabinet cabinet = findById(id);
        cabinetRepository.delete(cabinet);
    }
}
