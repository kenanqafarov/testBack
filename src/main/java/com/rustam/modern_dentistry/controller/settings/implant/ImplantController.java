package com.rustam.modern_dentistry.controller.settings.implant;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dto.request.create.ImplantCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantStatusUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.ImplantReadResponse;
import com.rustam.modern_dentistry.dto.response.read.ImplantResponse;
import com.rustam.modern_dentistry.service.settings.implant.ImplantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/implant")
@RequiredArgsConstructor
public class ImplantController {

    private final ImplantService implantService;

    @PostMapping(path = "/create")
    public ResponseEntity<ImplantResponse> create(@RequestBody ImplantCreateRequest implantCreateRequest){
        return new ResponseEntity<>(implantService.create(implantCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ImplantReadResponse>> read(){
        return new ResponseEntity<>(implantService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<ImplantReadResponse>> search(@RequestBody ImplantSearchRequest implantSearchRequest){
        return new ResponseEntity<>(implantService.search(implantSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ImplantResponse> update(@RequestBody ImplantUpdateRequest implantUpdateRequest){
        return new ResponseEntity<>(implantService.update(implantUpdateRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/status-update")
    public ResponseEntity<Status> statusUpdate(@RequestBody ImplantStatusUpdateRequest implantStatusUpdateRequest){
        return new ResponseEntity<>(implantService.statusUpdate(implantStatusUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        implantService.delete(id);
        return ResponseEntity.ok().build();
    }

}
