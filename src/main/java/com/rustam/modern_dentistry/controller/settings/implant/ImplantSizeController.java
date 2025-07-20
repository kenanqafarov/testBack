package com.rustam.modern_dentistry.controller.settings.implant;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dto.request.create.ImplantSizeCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSizeSearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSizeSearchStatusRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantSizeStatusUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ImplantSizeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.ImplantSizeResponse;
import com.rustam.modern_dentistry.service.settings.implant.ImplantSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/implant-size")
@RequiredArgsConstructor
public class ImplantSizeController {

    private final ImplantSizeService implantSizeService;

    @PostMapping(path = "/create")
    public ResponseEntity<ImplantSizeResponse> create(@RequestBody ImplantSizeCreateRequest implantSizeCreateRequest){
        return new ResponseEntity<>(implantSizeService.create(implantSizeCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ImplantSizeResponse>> read(){
        return new ResponseEntity<>(implantSizeService.read(),HttpStatus.OK);
    }

    @PostMapping(path = "/search-status")
    public ResponseEntity<List<ImplantSizeResponse>> searchStatus(@RequestBody ImplantSizeSearchStatusRequest implantSizeSearchRequest){
        return new ResponseEntity<>(implantSizeService.searchStatus(implantSizeSearchRequest),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<ImplantSizeResponse>> search(@RequestBody ImplantSizeSearchRequest implantSizeSearchRequest){
        return new ResponseEntity<>(implantSizeService.search(implantSizeSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/status-updated")
    public ResponseEntity<Status> statusUpdated(@RequestBody ImplantSizeStatusUpdateRequest request){
        return new ResponseEntity<>(implantSizeService.statusUpdated(request),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ImplantSizeResponse> update(@RequestBody ImplantSizeUpdateRequest implantSizeUpdateRequest){
        return new ResponseEntity<>(implantSizeService.update(implantSizeUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        implantSizeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
