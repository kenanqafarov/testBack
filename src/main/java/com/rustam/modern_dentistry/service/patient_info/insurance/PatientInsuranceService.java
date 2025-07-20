package com.rustam.modern_dentistry.service.patient_info.insurance;

import com.rustam.modern_dentistry.dao.entity.patient_info.insurance.PatientInsurance;
import com.rustam.modern_dentistry.dao.repository.patient_info.insurance.PatientInsuranceRepository;
import com.rustam.modern_dentistry.dto.request.create.PatientInsuranceCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.PatInsuranceUpdateReq;
import com.rustam.modern_dentistry.dto.request.update.PatInsuranceUpdateStatusReq;
import com.rustam.modern_dentistry.dto.response.read.PatientInsuranceReadResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.service.settings.InsuranceCompanyService;
import com.rustam.modern_dentistry.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rustam.modern_dentistry.mapper.patient_info.insurance.PatientInsuranceMapper.PATIENT_INSURANCE_MAPPER;

@Service
@RequiredArgsConstructor
public class PatientInsuranceService {
    private final UtilService utilService;
    private final PatientInsuranceRepository patientInsuranceRepository;
    private final InsuranceCompanyService insuranceCompanyService;

    public void create(PatientInsuranceCreateRequest request) {
        var check = patientInsuranceRepository.existsByPolicyNumber(request.getPolicyNumber());
        if (check) throw new ExistsException("Bu polis no artıq əlavə edilib.");

        var entity = PATIENT_INSURANCE_MAPPER.toEntity(request);
        var patient = utilService.findByPatientId(request.getPatientId());
        var insuranceCompany = insuranceCompanyService.getInsuranceById(request.getInsuranceCompanyId());
        entity.setPatient(patient);
        entity.setInsuranceCompany(insuranceCompany);
        patientInsuranceRepository.save(entity);
    }

    public List<PatientInsuranceReadResponse> readAllById(Long patientId) {
        var patientInsurances = patientInsuranceRepository.findByPatient_Id(patientId);
        return patientInsurances.stream()
                .map(PATIENT_INSURANCE_MAPPER::toReadDto)
                .toList();
    }

    public void update(Long id, PatInsuranceUpdateReq request) {
        var patientInsurance = getPatientInsuranceById(id);
        var insuranceCompany = insuranceCompanyService.getInsuranceById(request.getInsuranceCompanyId());
        PATIENT_INSURANCE_MAPPER.updatePatientInsurance(request, patientInsurance, insuranceCompany);
        patientInsuranceRepository.save(patientInsurance);
    }

    public List<PatientInsuranceReadResponse> updateStatus(Long id, PatInsuranceUpdateStatusReq request) {
        patientInsuranceRepository.updatePatientInsuranceStatus(id, request.getPatientId());
        return patientInsuranceRepository.findByPatient_Id(request.getPatientId())
                .stream()
                .map(PATIENT_INSURANCE_MAPPER::toReadDto)
                .toList();
    }

    public void delete(Long id) {
        var patientInsurance = getPatientInsuranceById(id);
        patientInsuranceRepository.delete(patientInsurance);
    }

    protected PatientInsurance getPatientInsuranceById(Long id) {
        return patientInsuranceRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də pasient sığortası tapımadı:" + id)
        );
    }
}
