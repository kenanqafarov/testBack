package com.rustam.modern_dentistry.service.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientRecipe;
import com.rustam.modern_dentistry.dao.repository.PatientRecipeRepository;
import com.rustam.modern_dentistry.dto.request.create.PatRecipeCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatRecipeUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatRecipeReadRes;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.patient_info.PatientRecipeMapper;
import com.rustam.modern_dentistry.service.settings.recipes.RecipeService;
import com.rustam.modern_dentistry.util.UtilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientRecipeService {
    private final UtilService utilService;
    private final RecipeService recipeService;
    private final PatientRecipeMapper patientRecipeMapper;
    private final PatientRecipeRepository patientRecipeRepository;

    public void create(PatRecipeCreateReq req) {
        var patient = utilService.findByPatientId(req.getPatientId());
        var recipe = recipeService.getRecipeById(req.getRecipeId());
        var patientRecipe = patientRecipeMapper.toEntity(req);
        patientRecipe.setPatient(patient);
        patientRecipe.setRecipe(recipe);
        patientRecipeRepository.save(patientRecipe);
    }

    public List<PatRecipeReadRes> readAllById(Long patientId) {
        var recipes = patientRecipeRepository.findByPatient_Id(patientId);
        return recipes.stream()
                .map(patientRecipeMapper::toDto)
                .toList();
    }

    public PatRecipeReadRes readById(Long id) {
        var entity = getPatientRecipeById(id);
        return patientRecipeMapper.toDto(entity);
    }

    public void update(Long id, @Valid PatRecipeUpdateReq request) {
        var patientRecipe = getPatientRecipeById(id);
        var recipe = recipeService.getRecipeById(request.getRecipeId());
        patientRecipeMapper.update(patientRecipe, request, recipe);
        patientRecipeRepository.save(patientRecipe);
    }

    public void delete(Long id) {
        var patientRecipe = getPatientRecipeById(id);
        patientRecipeRepository.delete(patientRecipe);
    }

    private PatientRecipe getPatientRecipeById(Long id) {
        return patientRecipeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də istifadəçi resepti tapımadı:" + id)
        );
    }
}
