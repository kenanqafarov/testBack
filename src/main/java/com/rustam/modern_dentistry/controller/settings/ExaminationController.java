package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.CreateExaminationRequest;
import com.rustam.modern_dentistry.dto.request.create.ExaminationUpdateRequest;
import com.rustam.modern_dentistry.dto.request.read.ExaminationRequest;
import com.rustam.modern_dentistry.dto.response.read.ExaminationResponse;
import com.rustam.modern_dentistry.service.settings.ExaminationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/examination")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    @PostMapping(path = "/create")
    public ResponseEntity<Void> createExamination(@Valid @RequestBody CreateExaminationRequest createExaminationRequest){
        examinationService.createExamination(createExaminationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ExaminationResponse>> read(){
        return new ResponseEntity<>(examinationService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<ExaminationResponse>> search(@RequestBody ExaminationRequest examinationRequest){
        return new ResponseEntity<>(examinationService.search(examinationRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update-status")
    public ResponseEntity<ExaminationResponse> updateStatus(@RequestBody ExaminationRequest examinationRequest){
        return new ResponseEntity<>(examinationService.updateStatus(examinationRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ExaminationResponse> update(@RequestBody ExaminationUpdateRequest examinationUpdateRequest){
        return new ResponseEntity<>(examinationService.update(examinationUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        examinationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
