package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.enums.Role;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dao.repository.PatientRepository;
import com.rustam.modern_dentistry.dto.request.create.PatientCreateRequest;
import com.rustam.modern_dentistry.dto.request.read.PatientSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.PatientUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.PatientCreateResponse;
import com.rustam.modern_dentistry.dto.response.excel.PatientExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.PatientReadResponse;
import com.rustam.modern_dentistry.dto.response.update.PatientUpdateResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.mapper.PatientMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Map.entry;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PatientService {

    PatientRepository patientRepository;
    UtilService utilService;
    PatientMapper patientMapper;
    ModelMapper modelMapper;
    DoctorService doctorService;

    public PatientCreateResponse create(PatientCreateRequest patientCreateRequest) {
        if (existsByEmailAndFinCode(patientCreateRequest.getEmail(), patientCreateRequest.getFinCode())) {
            throw new ExistsException("bu email ve ya finkod movcutdur");
        }
        Doctor doctor = doctorService.findById(patientCreateRequest.getDoctor_id());
        Patient patient = Patient.builder()
                .name(patientCreateRequest.getName())
                .surname(patientCreateRequest.getSurname())
                .patronymic(patientCreateRequest.getPatronymic())
                .finCode(patientCreateRequest.getFinCode())
                .dateOfBirth(patientCreateRequest.getDateOfBirth())
                .phone(patientCreateRequest.getPhone())
                .email(patientCreateRequest.getEmail())
                .enabled(true)
                .doctor(doctor)
                .homePhone(patientCreateRequest.getHomePhone())
                .workPhone(patientCreateRequest.getWorkPhone())
                .workAddress(patientCreateRequest.getWorkAddress())
                .homeAddress(patientCreateRequest.getHomeAddress())
                .genderStatus(patientCreateRequest.getGenderStatus())
                .priceCategoryStatus(patientCreateRequest.getPriceCategoryStatus())
                .specializationStatus(patientCreateRequest.getSpecializationStatus())
                .registration_date(LocalDate.now())
                .build();
        patientRepository.save(patient);
        PatientCreateResponse patientCreateResponse = new PatientCreateResponse();
        modelMapper.map(patient, patientCreateResponse);
        return patientCreateResponse;
    }

    public PatientUpdateResponse update(PatientUpdateRequest patientUpdateRequest) {
        Patient patient = utilService.findByPatientId(patientUpdateRequest.getPatientId());
        updatePatientFromRequest(patient, patientUpdateRequest);
        patientRepository.save(patient);
        return patientMapper.toUpdatePatient(patient);
    }

    private void updatePatientFromRequest(Patient patient, PatientUpdateRequest request) {
        utilService.updateFieldIfPresent(request.getName(), patient::setName);
        utilService.updateFieldIfPresent(request.getSurname(), patient::setSurname);
        utilService.updateFieldIfPresent(request.getPatronymic(), patient::setPatronymic);
        utilService.updateFieldIfPresent(request.getFinCode(), patient::setFinCode);
        utilService.updateFieldIfPresent(request.getGenderStatus(), patient::setGenderStatus);
        utilService.updateFieldIfPresent(request.getDateOfBirth(), patient::setDateOfBirth);
        utilService.updateFieldIfPresent(request.getPriceCategoryStatus(), patient::setPriceCategoryStatus);
        utilService.updateFieldIfPresent(request.getSpecializationStatus(), patient::setSpecializationStatus);

        if (request.getDoctor_id() != null) {
            Doctor doctor = doctorService.findById(request.getDoctor_id());
            patient.setDoctor(doctor);
        }

        utilService.updateFieldIfPresent(request.getPhone(), patient::setPhone);
        utilService.updateFieldIfPresent(request.getWorkPhone(), patient::setWorkPhone);
        utilService.updateFieldIfPresent(request.getHomePhone(), patient::setHomePhone);
        utilService.updateFieldIfPresent(request.getHomeAddress(), patient::setHomeAddress);
        utilService.updateFieldIfPresent(request.getWorkAddress(), patient::setWorkAddress);
        utilService.updateFieldIfPresent(request.getEmail(), patient::setEmail);
    }


    public List<PatientReadResponse> read() {
        List<Patient> users = patientRepository.findAll();
        return patientMapper.toDtos(users);
    }

    public PatientReadResponse readById(Long id) {
        Patient patient = utilService.findByPatientId(id);
        return patientMapper.toRead(patient);
    }

    public String delete(Long id) {
        Patient patient = utilService.findByPatientId(id);
        patientRepository.delete(patient);
        return "Qeydiyyatdan silindi";
    }

    public List<PatientReadResponse> search(PatientSearchRequest patientSearchRequest) {
        List<Patient> byNameAndSurnameAndFinCodeAndGenderStatusAndPhone =
                patientRepository.findAll(UserSpecification.filterBy(patientSearchRequest));
        return patientMapper.toDtos(byNameAndSurnameAndFinCodeAndGenderStatusAndPhone);
    }

    public InputStreamResource exportReservationsToExcel() {
        List<Patient> patients = patientRepository.findAll();
        var list = patients.stream().map(patientMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, PatientExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    public Boolean existsByEmailAndFinCode(String email, String finCode) {
        return patientRepository.existsByEmailOrFinCode(email, finCode);
    }
}
