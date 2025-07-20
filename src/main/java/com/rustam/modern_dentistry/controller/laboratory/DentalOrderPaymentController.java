package com.rustam.modern_dentistry.controller.laboratory;

import com.rustam.modern_dentistry.dto.request.create.DentalOrderPaymentCreateReq;
import com.rustam.modern_dentistry.dto.response.read.TechnicianReportResponse;
import com.rustam.modern_dentistry.service.labarotory.DentalOrderPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "/api/v1/laboratory-payment")
@RequiredArgsConstructor
public class DentalOrderPaymentController {
    private final DentalOrderPaymentService dentalOrderPaymentService;

    @PostMapping(path = "/create")
    public ResponseEntity<Void> create(@RequestBody @Valid DentalOrderPaymentCreateReq request) {
        dentalOrderPaymentService.createPayment(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<TechnicianReportResponse>> read() {
        return ResponseEntity.ok(dentalOrderPaymentService.read());
    }
}
