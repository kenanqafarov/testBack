package com.rustam.modern_dentistry.controller;

import com.rustam.modern_dentistry.dto.request.create.WorkersWorkScheduleRequest;
import com.rustam.modern_dentistry.dto.request.read.WorkersWorkScheduleSearchRequest;
import com.rustam.modern_dentistry.dto.response.read.WorkersWorkScheduleResponse;
import com.rustam.modern_dentistry.dto.response.update.WorkersWorkScheduleUpdateDTO;
import com.rustam.modern_dentistry.service.WorkersWorkScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/workers-work-schedule")
@RequiredArgsConstructor
public class WorkersWorkScheduleController {

    private final WorkersWorkScheduleService workersWorkScheduleService;

    @PostMapping(path = "/create")
    public ResponseEntity<Void> create(@Valid @RequestBody WorkersWorkScheduleRequest workersWorkScheduleRequest){
        workersWorkScheduleService.create(workersWorkScheduleRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<WorkersWorkScheduleResponse>> read(){
        return new ResponseEntity<>(workersWorkScheduleService.read(), HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<WorkersWorkScheduleResponse>> search(@RequestBody WorkersWorkScheduleSearchRequest workersWorkScheduleSearchRequest){
        return new ResponseEntity<>(workersWorkScheduleService.search(workersWorkScheduleSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<WorkersWorkScheduleUpdateDTO> update(@RequestBody WorkersWorkScheduleUpdateDTO workersWorkScheduleUpdateDTO){
        return new ResponseEntity<>(workersWorkScheduleService.update(workersWorkScheduleUpdateDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        workersWorkScheduleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
