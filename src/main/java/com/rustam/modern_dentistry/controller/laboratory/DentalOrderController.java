package com.rustam.modern_dentistry.controller.laboratory;

import com.rustam.modern_dentistry.dto.request.DentalOrderCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateLabOrderStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateOrderPrice;
import com.rustam.modern_dentistry.dto.request.update.UpdateTechnicianOrderReq;
import com.rustam.modern_dentistry.dto.response.read.TechnicianOrderResponse;
import com.rustam.modern_dentistry.service.labarotory.DentalOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/api/v1/laboratory")
@RequiredArgsConstructor
public class DentalOrderController {
    private final DentalOrderService dentalOrderService;

    @PostMapping(path = "/order/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart("data") @Valid DentalOrderCreateReq request,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        dentalOrderService.create(request, files);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/order/read")
    public ResponseEntity<List<TechnicianOrderResponse>> read() {
        return ResponseEntity.ok(dentalOrderService.read());
    }

    @GetMapping("/order/read-by-id/{id}")
    public ResponseEntity<TechnicianOrderResponse> readById(@PathVariable Long id) {
        return ResponseEntity.ok(dentalOrderService.readById(id));
    }

    @PutMapping(path = "/order/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart("data") @Valid UpdateTechnicianOrderReq request,
                                       @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles) {
        dentalOrderService.update(id, request, newFiles);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/order/status")
    public ResponseEntity<Void> updateOrderStatus(UpdateLabOrderStatus request) {
        dentalOrderService.updateOrderStatus(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/order/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dentalOrderService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // Technic apis
    // After user login get technic's orders
    // @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/technic/order/read")
    public ResponseEntity<List<TechnicianOrderResponse>> getTechnicianOrdersByUUID() {
        return ResponseEntity.ok(dentalOrderService.getTechnicianOrdersByUUID());
    }

    @PatchMapping(path = "/technic/order/{id}/price")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateOrderPrice request) {
        dentalOrderService.setOrderPrice(id, request);
        return ResponseEntity.ok().build();
    }

}
