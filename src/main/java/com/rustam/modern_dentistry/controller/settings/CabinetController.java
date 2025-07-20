package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Cabinet;
import com.rustam.modern_dentistry.dto.request.create.CabinetCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.CabinetSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.CabinetStatusUpdatedRequest;
import com.rustam.modern_dentistry.dto.request.update.CabinetUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.CabinetResponse;
import com.rustam.modern_dentistry.dto.response.update.CabinetStatusUpdatedResponse;
import com.rustam.modern_dentistry.service.settings.CabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cabinet")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;

    @PostMapping(path = "/create")
    public ResponseEntity<CabinetResponse> create(@RequestBody CabinetCreateRequest cabinetCreateRequest){
        return new ResponseEntity<>(cabinetService.create(cabinetCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<CabinetResponse>> read(){
        return new ResponseEntity<>(cabinetService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<CabinetResponse>> search(@RequestBody CabinetSearchRequest cabinetSearchRequest){
        return new ResponseEntity<>(cabinetService.search(cabinetSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CabinetResponse> update(@RequestBody CabinetUpdateRequest cabinetUpdateRequest){
        return new ResponseEntity<>(cabinetService.update(cabinetUpdateRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/status-updated")
    public ResponseEntity<CabinetStatusUpdatedResponse> statusUpdated(@RequestBody CabinetStatusUpdatedRequest cabinetStatusUpdatedRequest){
        return new ResponseEntity<>(cabinetService.statusUpdated(cabinetStatusUpdatedRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cabinetService.delete(id);
        return ResponseEntity.ok().build();
    }
}
