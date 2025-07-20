package com.rustam.modern_dentistry.controller.settings;

import com.rustam.modern_dentistry.dto.request.create.AppointmentTypeCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.AppointmentTypeSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.AppointmentTypeUpdateRequest;
import com.rustam.modern_dentistry.dto.request.update.ProductCategoryStatusUpdatedRequest;
import com.rustam.modern_dentistry.dto.response.read.AppointmentTypeResponse;
import com.rustam.modern_dentistry.service.settings.AppointmentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/appointment-type")
@RequiredArgsConstructor
public class AppointmentTypeController {

    private final AppointmentTypeService appointmentTypeService;

    @PostMapping(path = "/create")
    public ResponseEntity<AppointmentTypeResponse> create(@Valid @RequestBody AppointmentTypeCreateRequest appointmentTypeCreateRequest){
        return new ResponseEntity<>(appointmentTypeService.create(appointmentTypeCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<AppointmentTypeResponse>> read(){
        return new ResponseEntity<>(appointmentTypeService.read(),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<AppointmentTypeResponse> update(@RequestBody AppointmentTypeUpdateRequest appointmentTypeUpdateRequest){
        return new ResponseEntity<>(appointmentTypeService.update(appointmentTypeUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        appointmentTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<AppointmentTypeResponse>> search(@RequestBody AppointmentTypeSearchRequest appointmentTypeSearchRequest){
        return new ResponseEntity<>(appointmentTypeService.search(appointmentTypeSearchRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/status-updated")
    public ResponseEntity<AppointmentTypeResponse> statusUpdated(@RequestBody ProductCategoryStatusUpdatedRequest productCategoryStatusUpdatedRequest){
        return new ResponseEntity<>(appointmentTypeService.statusUpdated(productCategoryStatusUpdatedRequest),HttpStatus.OK);
    }
}
