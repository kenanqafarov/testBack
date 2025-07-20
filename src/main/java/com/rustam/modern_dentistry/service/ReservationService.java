package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.Reservation;
import com.rustam.modern_dentistry.dao.repository.ReservationRepository;
import com.rustam.modern_dentistry.dto.request.create.ReservationCreateRequest;
import com.rustam.modern_dentistry.dto.request.criteria.PageCriteria;
import com.rustam.modern_dentistry.dto.request.read.ReservationSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.ReservationUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.ReservationCreateResponse;
import com.rustam.modern_dentistry.dto.response.excel.ReservationExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.PageResponse;
import com.rustam.modern_dentistry.dto.response.read.ReservationReadResponse;
import com.rustam.modern_dentistry.dto.response.update.ReservationUpdateResponse;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.ReservationMapper;
import com.rustam.modern_dentistry.util.ExcelUtil;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.ReservationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.ReservationStatus.ACTIVE;
import static com.rustam.modern_dentistry.dao.entity.enums.status.ReservationStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final DoctorService doctorService;
    private final UtilService utilService;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationCreateResponse create(ReservationCreateRequest request) {
        var doctor = doctorService.findById(request.getDoctorId());
        var patient = utilService.findByPatientId(request.getPatientId());
        var queueReservation = reservationMapper.toEntity(request, doctor, patient);
        return reservationMapper.toCreateDto(reservationRepository.save(queueReservation));
    }

    public PageResponse<ReservationReadResponse> read(PageCriteria pageCriteria) {
        var reservations = reservationRepository.findAll(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var reservationReadResponse = getContentResponse(reservations.getContent());
        return new PageResponse<>(reservations.getTotalPages(), reservations.getTotalElements(), reservationReadResponse);
    }

    public ReservationReadResponse readById(Long id) {
        var reservation = getReservationById(id);
        return reservationMapper.toReadDto(reservation);
    }

    public ReservationUpdateResponse update(Long id, ReservationUpdateRequest request) {
        var reservation = getReservationById(id);
        var doctor = doctorService.findById(request.getDoctorId());
        var patient = utilService.findByPatientId(request.getPatientId());
        var updateReservation = reservationMapper.updateReservation(reservation, request, doctor, patient);
        return reservationMapper.toUpdateDto(reservationRepository.save(updateReservation));
    }

    public void updateStatus(Long id) {
        var reservation = getReservationById(id);
        var status = reservation.getStatus() == ACTIVE ? PASSIVE : ACTIVE;
        reservation.setStatus(status);
        reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        var reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }

    public PageResponse<ReservationReadResponse> search(ReservationSearchRequest request, PageCriteria pageCriteria) {
        Page<Reservation> response = reservationRepository.findAll(
                ReservationSpecification.filterBy(request),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        var reservationReadResponse = getContentResponse(response.getContent());
        return new PageResponse<>(response.getTotalPages(), response.getTotalElements(), reservationReadResponse);
    }

    public InputStreamResource exportReservationsToExcel() {
        List<Reservation> reservations = reservationRepository.findAll();
        var list = reservations.stream().map(reservationMapper::toExcelDto).toList();
        ByteArrayInputStream excelFile = ExcelUtil.dataToExcel(list, ReservationExcelResponse.class);
        return new InputStreamResource(excelFile);
    }

    private Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də növbə tapımadı: " + id)
        );
    }

    private List<ReservationReadResponse> getContentResponse(List<Reservation> content) {
        return content.stream().map((reservationMapper::toReadDto)).toList();
    }
}
