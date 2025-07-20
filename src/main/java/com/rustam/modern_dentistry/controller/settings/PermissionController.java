package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dto.request.create.PermissionCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.PermissionSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.PermissionStatusUpdatedRequest;
import com.rustam.modern_dentistry.dto.request.update.PermissionUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.PermissionCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.InfoPermissionResponse;
import com.rustam.modern_dentistry.dto.response.read.PermissionResponse;
import com.rustam.modern_dentistry.service.settings.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping(path = "/create")
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionCreateRequest permissionCreateRequest){
        return new ResponseEntity<>(permissionService.create(permissionCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<PermissionResponse>> read(){
        return new ResponseEntity<>(permissionService.read(),HttpStatus.OK);
    }

    @GetMapping(path = "/info/{id}")
    public ResponseEntity<InfoPermissionResponse> info(@PathVariable Long id){
        return new ResponseEntity<>(permissionService.info(id),HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<PermissionResponse>> search(@RequestBody PermissionSearchRequest permissionSearchRequest){
        return new ResponseEntity<>(permissionService.search(permissionSearchRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        permissionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/status-updated")
    public ResponseEntity<PermissionResponse> statusUpdated(@RequestBody PermissionStatusUpdatedRequest permissionStatusUpdatedRequest){
        return new ResponseEntity<>(permissionService.statusUpdated(permissionStatusUpdatedRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<PermissionCreateResponse> update(@RequestBody PermissionUpdateRequest permissionUpdateRequest){
        return new ResponseEntity<>(permissionService.update(permissionUpdateRequest),HttpStatus.OK);
    }

}
