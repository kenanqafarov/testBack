package com.rustam.modern_dentistry.service.patient_info;

import com.rustam.modern_dentistry.dao.entity.Examination;
import com.rustam.modern_dentistry.dao.entity.patient_info.PatientExaminations;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethExamination;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.request.read.RequestToSeeTheExaminations;
import com.rustam.modern_dentistry.dto.response.read.SelectingPatientToReadResponse;
import com.rustam.modern_dentistry.service.DoctorService;
import com.rustam.modern_dentistry.dao.repository.PatientExaminationsRepository;
import com.rustam.modern_dentistry.dto.request.create.PatientExaminationsCreateRequest;
import com.rustam.modern_dentistry.dto.request.create.PatientExaminationsUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.PatientExaminationsCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.ExaminationResponse;
import com.rustam.modern_dentistry.dto.response.read.PatientExaminationsResponse;
import com.rustam.modern_dentistry.dto.response.read.TeethResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.service.GeneralCalendarService;
import com.rustam.modern_dentistry.service.settings.ExaminationService;
import com.rustam.modern_dentistry.service.settings.teeth.TeethService;
import com.rustam.modern_dentistry.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientExaminationsService {

    PatientExaminationsRepository patientExaminationsRepository;
    ExaminationService examinationService;
    TeethService teethService;
    DoctorService doctorService;
    UtilService utilService;
    GeneralCalendarService generalCalendarService;

    public List<ExaminationResponse> readExaminations() {
        return examinationService.read();
    }

    public List<TeethResponse> readTeeth() {
        return teethService.read();
    }

    public PatientExaminationsCreateResponse create(PatientExaminationsCreateRequest request) {
        Examination examination = examinationService.findById(request.getExaminationId());

        boolean patientHasExamination = patientExaminationsRepository
                .existsPatientExaminationsByPatientAndToothNumberAndDiagnosis(request.getPatientId(),request.getToothNumber() ,examination.getTypeName());
        if (patientHasExamination) {
            throw new ExistsException("Examination " + examination.getTypeName() +
                    " is already recorded for this patient.");
        }

        List<TeethExamination> existingExaminations = teethService.dentalExaminationForTeethOrThrow(request.getToothNumber());

        Map<Long, List<String>> existingExaminationsMap = existingExaminations.stream()
                .collect(Collectors.groupingBy(
                        te -> te.getTeeth().getToothNo(),
                        Collectors.mapping(te -> te.getExamination().getTypeName(), Collectors.toList())
                ));

        boolean hasMatchingExamination = request.getToothNumber().stream()
                .anyMatch(toothNumber -> existingExaminationsMap
                        .getOrDefault(toothNumber, List.of())
                        .contains(examination.getTypeName()));

        if (!hasMatchingExamination) {
            throw new ExistsException("Examination " + examination.getTypeName() +
                    " is not recorded for any of the selected teeth: " + request.getToothNumber());
        }

        SelectingPatientToReadResponse patientData = generalCalendarService.findByPatientId(request.getPatientId());
        String currentUserId = utilService.getCurrentUserId();

        List<PatientExaminations> patientExaminationsList = request.getToothNumber().stream()
                .map(toothNumber -> PatientExaminations.builder()
                        .patient(utilService.findByPatientId(request.getPatientId()))
                        .toothNumber(toothNumber)
                        .diagnosis(examination.getTypeName())
                        .doctorId(UUID.fromString(currentUserId))
                        .patientAppointmentDate(patientData.getDate())
                        .build())
                .collect(Collectors.toList());

        patientExaminationsRepository.saveAll(patientExaminationsList);

        return PatientExaminationsCreateResponse.builder()
                .patientId(request.getPatientId())
                .toothNo(request.getToothNumber())
                .diagnosis(examination.getTypeName())
                .doctorId(currentUserId)
                .build();
    }


    public PatientExaminations findById(Long id) {
        return patientExaminationsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This patient does not have these tests."));
    }

    public PatientExaminationsCreateResponse update(PatientExaminationsUpdateRequest patientExaminationsUpdateRequest) {
        PatientExaminations patientExaminations = findById(patientExaminationsUpdateRequest.getId());
        Patient patient = utilService.findByPatientId(patientExaminationsUpdateRequest.getPatientId());
        Examination examination = examinationService.findById(patientExaminationsUpdateRequest.getExaminationId());
        boolean existsPatientExaminationsByPatientAndToothNumber = patientExaminationsRepository.existsPatientExaminationsByPatientAndToothNumberAndDiagnosis(patientExaminationsUpdateRequest.getPatientId(), patientExaminationsUpdateRequest.getToothNumber(),examination.getTypeName());
        if (existsPatientExaminationsByPatientAndToothNumber) {
            throw new ExistsException("These examinations are available for this patient.");
        }
        List<Long> teethNo = new ArrayList<>();
        patientExaminationsUpdateRequest.getToothNumber().forEach(toothNumber -> {
            patientExaminations.setPatient(patient);
            patientExaminations.setToothNumber(toothNumber);
            patientExaminations.setDiagnosis(examination.getTypeName());
            patientExaminationsRepository.save(patientExaminations);
            teethNo.add(toothNumber);
        });
        return PatientExaminationsCreateResponse.builder()
                .patientId(patientExaminationsUpdateRequest.getPatientId())
                .toothNo(teethNo)
                .diagnosis(examination.getTypeName())
                .build();
    }

    public List<PatientExaminationsResponse> read() {
        return patientExaminationsRepository.findAllPatientExaminations();
    }

    public void delete(Long id) {
        PatientExaminations patientExaminations = findById(id);
        patientExaminationsRepository.delete(patientExaminations);
    }

    public List<PatientExaminationsResponse> seeHistoricalElectionDentalExaminations(RequestToSeeTheExaminations requestToSeeTheExaminations) {
        return patientExaminationsRepository.findByAppointmentDate(requestToSeeTheExaminations.getDate());
    }

}
