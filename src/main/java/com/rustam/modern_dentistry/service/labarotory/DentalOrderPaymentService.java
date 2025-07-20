package com.rustam.modern_dentistry.service.labarotory;

import com.rustam.modern_dentistry.dao.entity.laboratory.DentalOrderPayment;
import com.rustam.modern_dentistry.dao.entity.users.Technician;
import com.rustam.modern_dentistry.dao.repository.laboratory.DentalOrderPaymentRepository;
import com.rustam.modern_dentistry.dto.request.create.DentalOrderPaymentCreateReq;
import com.rustam.modern_dentistry.dto.response.read.TechnicianReportResponse;
import com.rustam.modern_dentistry.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DentalOrderPaymentService {
    private final TechnicianService technicianService;
    private final DentalOrderPaymentRepository dentalOrderPaymentRepository;

    public void createPayment(DentalOrderPaymentCreateReq request) {
        Technician technician = technicianService.getTechnicianById(request.getTechnicianId());

        DentalOrderPayment payment = DentalOrderPayment.builder()
                .amount(request.getAmount())
                .paymentDate(LocalDate.now())
                .technician(technician)
                .build();

        dentalOrderPaymentRepository.save(payment);
    }

    public List<TechnicianReportResponse> read() {
        return dentalOrderPaymentRepository.getTechnicianReportOptimized();
    }
}
