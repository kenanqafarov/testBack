package com.rustam.modern_dentistry.service.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.recipes.Recipe;
import com.rustam.modern_dentistry.dao.repository.settings.recipes.RecipeRepository;
import com.rustam.modern_dentistry.dto.request.create.RecipeCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.dto.request.update.RecipeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.RecipeReadResponse;
import com.rustam.modern_dentistry.dto.response.excel.RecipeExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.settings.recipes.RecipeMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.specification.settings.recipes.RecipeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeMapper recipeMapper;
    private final RecipeRepository recipeRepository;

    public void create(RecipeCreateRequest request) {
        checkNameIfAlreadyExist(request.getName());
        Recipe recipe = recipeMapper.toEntity(request);
        recipeRepository.save(recipe);
    }

    public PageResponse<RecipeReadResponse> read(PageCriteria pageCriteria) {
        var recipes = recipeRepository.findAll(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return new PageResponse<>(
                recipes.getTotalPages(),
                recipes.getTotalElements(),
                getContent(recipes.getContent()));
    }

    public List<RecipeReadResponse> readList() {
        var recipes = recipeRepository.findAll();
        return recipes.stream().map(recipeMapper::toReadDto).toList();
    }

    public RecipeReadResponse readById(Long id) {
        var recipe = getRecipeById(id);
        return recipeMapper.toReadDto(recipe);
    }

    public void update(Long id, RecipeUpdateRequest request) {
        var recipe = getRecipeById(id);
        recipeMapper.update(recipe, request);
        recipeRepository.save(recipe);
    }

    public void updateStatus(Long id) {
        var recipe = getRecipeById(id);
        recipe.setStatus(recipe.getStatus() == ACTIVE ? Status.PASSIVE : ACTIVE);
        recipeRepository.save(recipe);
    }

    public void delete(Long id) {
        var recipe = getRecipeById(id);
        recipeRepository.delete(recipe);
    }

    public PageResponse<RecipeReadResponse> search(PageCriteria pageCriteria, SearchByNameAndStatus request) {
        var response = recipeRepository.findAll(
                RecipeSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var medicines = getContent(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), medicines);
    }

    public InputStreamResource exportReservationsToExcel() {
        var recipes = recipeRepository.findAll();
        var list = recipes.stream().map(recipeMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, RecipeExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private void checkNameIfAlreadyExist(String name) {
        var result = recipeRepository.existsByNameIgnoreCase(name);
        if (result) throw new ExistsException("Bu ad artıq əlavə edilib.");
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də resept tapımadı: " + id));
    }

    private List<RecipeReadResponse> getContent(List<Recipe> content) {
        return content.stream().map(recipeMapper::toReadDto).toList();
    }
}
