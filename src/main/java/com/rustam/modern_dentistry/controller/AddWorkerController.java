package com.rustam.modern_dentistry.controller;

import com.rustam.modern_dentistry.dto.request.create.AddWorkerCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.AddWorkerSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.AddWorkerUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.AddWorkerCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.AddWorkerReadResponse;
import com.rustam.modern_dentistry.dto.response.read.AddWorkerReadStatusResponse;
import com.rustam.modern_dentistry.dto.response.update.AddWorkerUpdateResponse;
import com.rustam.modern_dentistry.service.AddWorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/add-worker")
@RequiredArgsConstructor
public class AddWorkerController {

    private final AddWorkerService addWorkerService;

    @PostMapping(path = "/create")
    public ResponseEntity<AddWorkerCreateResponse> create(@Valid @RequestBody AddWorkerCreateRequest addWorkerCreateRequest){
        return new ResponseEntity<>(addWorkerService.create(addWorkerCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<AddWorkerReadResponse>> read(){
        return new ResponseEntity<>(addWorkerService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/read-permission/{permission}")
    public ResponseEntity<List<AddWorkerReadResponse>> readPermission(@PathVariable String permission){
        return new ResponseEntity<>(addWorkerService.readPermission(permission),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<AddWorkerUpdateResponse> update(@Valid @RequestBody AddWorkerUpdateRequest addWorkerUpdateRequest){
        return new ResponseEntity<>(addWorkerService.update(addWorkerUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        return new ResponseEntity<>(addWorkerService.delete(id),HttpStatus.OK);
    }

    @GetMapping(path = "/info/{id}")
    public ResponseEntity<AddWorkerReadResponse> info(@PathVariable UUID id){
        return new ResponseEntity<>(addWorkerService.info(id),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<AddWorkerReadResponse>> search(@RequestBody AddWorkerSearchRequest addWorkerSearchRequest){
        return new ResponseEntity<>(addWorkerService.search(addWorkerSearchRequest),HttpStatus.OK);
    }

    @GetMapping(path = "/read-roles")
    public ResponseEntity<List<AddWorkerReadStatusResponse>> readStatus(){
        return new ResponseEntity<>(addWorkerService.readStatus(),HttpStatus.OK);
    }
}
