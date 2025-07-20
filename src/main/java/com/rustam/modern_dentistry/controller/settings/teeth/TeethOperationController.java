package com.rustam.modern_dentistry.controller.settings.teeth;

import com.rustam.modern_dentistry.dto.request.create.CreateTeethOperationRequest;
import com.rustam.modern_dentistry.dto.request.read.SearchTeethOperationRequest;
import com.rustam.modern_dentistry.dto.request.update.UpdateTeethOperationRequest;
import com.rustam.modern_dentistry.dto.response.read.TeethOperationResponse;
import com.rustam.modern_dentistry.service.settings.teeth.TeethOperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teeth-operation")
@RequiredArgsConstructor
public class TeethOperationController {

    private final TeethOperationService teethOperationService;

    @PostMapping(path = "/create")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateTeethOperationRequest createTeethOperationRequest){
        teethOperationService.create(createTeethOperationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<TeethOperationResponse>> read(){
        return new ResponseEntity<>(teethOperationService.read(), HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<TeethOperationResponse>> search(@RequestBody SearchTeethOperationRequest searchTeethOperationRequest){
        return new ResponseEntity<>(teethOperationService.search(searchTeethOperationRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<TeethOperationResponse> update(@RequestBody UpdateTeethOperationRequest updateTeethOperationRequest){
        return new ResponseEntity<>(teethOperationService.update(updateTeethOperationRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        teethOperationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
