package com.rustam.modern_dentistry.service.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.recipes.Medicine;
import com.rustam.modern_dentistry.dao.repository.settings.recipes.MedicineRepository;
import com.rustam.modern_dentistry.dto.request.create.MedicineCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.MedicineSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.MedicineUpdateRequest;
import com.rustam.modern_dentistry.dto.response.excel.MedicineExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.MedicineReadResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.recipes.MedicineMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.recipes.MedicineSpecification;
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
public class MedicineService {
    private final MedicineMapper medicineMapper;
    private final RecipeService recipeService;
    private final MedicineRepository medicineRepository;

    public void create(MedicineCreateRequest request) {
        var recipe = recipeService.getRecipeById(request.getRecipeId());
        var medicine = medicineMapper.toEntity(request);
        medicine.setRecipe(recipe);
        medicineRepository.save(medicine);
    }

    public PageResponse<MedicineReadResponse> read(Long recipeId, PageCriteria pageCriteria) {
        var medicines = getMedicinesByRecipeId(recipeId, pageCriteria);
        return new PageResponse<>(
                medicines.getTotalPages(),
                medicines.getTotalElements(),
                getContent(medicines.getContent()));
    }

    public MedicineReadResponse readById(Long id) {
        var medicine = getMedicineById(id);
        return medicineMapper.toReadDto(medicine);
    }

    public void update(Long id, MedicineUpdateRequest request) {
        var medicine = getMedicineById(id);
        medicineMapper.update(medicine, request);
        medicineRepository.save(medicine);
    }

    public void updateStatus(Long id) {
        var medicine = getMedicineById(id);
        Status status = medicine.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        medicine.setStatus(status);
        medicineRepository.save(medicine);
    }

    public void delete(Long id) {
        var medicine = getMedicineById(id);
        medicineRepository.delete(medicine);
    }

    public PageResponse<MedicineReadResponse> search(PageCriteria pageCriteria, MedicineSearchRequest request) {
        var response = medicineRepository.findAll(
                MedicineSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var medicines = getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), medicines);
    }

    public InputStreamResource exportReservationsToExcel() {
        var medicines = medicineRepository.findAll();
        var list = medicines.stream().map(medicineMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, MedicineExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private List<MedicineReadResponse> getContent(List<Medicine> content) {
        return content.stream().map(medicineMapper::toReadDto).toList();
    }

    private Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də dərman tapımadı: " + id)
        );
    }

    private Page<Medicine> getMedicinesByRecipeId(Long recipeId, PageCriteria pageCriteria) {
        return medicineRepository.findByRecipe_Id(
                recipeId, PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
    }
}
