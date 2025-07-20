package com.rustam.modern_dentistry.controller.settings.teeth;

import com.rustam.modern_dentistry.dto.request.create.TeethExaminationRequest;
import com.rustam.modern_dentistry.dto.request.read.TeethExaminationSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.TeethExaminationUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.TeethExaminationResponse;
import com.rustam.modern_dentistry.service.settings.teeth.TeethExaminationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teeth-examination")
@RequiredArgsConstructor
public class TeethExaminationController {

    private final TeethExaminationService teethExaminationService;

    @PostMapping(path = "/create")
    public ResponseEntity<TeethExaminationResponse> create(@Valid @RequestBody TeethExaminationRequest teethExaminationRequest){
        return new ResponseEntity<>(teethExaminationService.create(teethExaminationRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<TeethExaminationResponse>> read(){
        return new ResponseEntity<>(teethExaminationService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<TeethExaminationResponse>> search(@RequestBody TeethExaminationSearchRequest teethExaminationSearchRequest){
        return new ResponseEntity<>(teethExaminationService.search(teethExaminationSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<TeethExaminationResponse> update(@Valid @RequestBody TeethExaminationUpdateRequest teethExaminationUpdateRequest){
        return new ResponseEntity<>(teethExaminationService.update(teethExaminationUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        teethExaminationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
