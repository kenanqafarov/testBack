package com.rustam.modern_dentistry.util.specification;

import com.rustam.modern_dentistry.dao.entity.Reservation;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.request.read.ReservationSearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReservationSpecification {

    public static Specification<Reservation> filterBy(ReservationSearchRequest request) {

        return (root, query, cb) -> {

            root.fetch("doctor", JoinType.LEFT);
            root.fetch("patient", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (request.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), request.getStartDate()));
            }

            if (request.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), request.getEndDate()));
            }

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }

            if (request.getDoctorName() != null && !request.getDoctorName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("doctor").get("name")), "%" + request.getDoctorName().toLowerCase() + "%"));
            }

            if (request.getDoctorSurname() != null && !request.getDoctorSurname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("doctor").get("surname")), "%" + request.getDoctorSurname().toLowerCase() + "%"));
            }

            if (request.getPatientName() != null && !request.getPatientName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("patient").get("name")), "%" + request.getPatientName().toLowerCase() + "%"));
            }

            if (request.getPatientSurname() != null && !request.getPatientSurname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("patient").get("surname")), "%" + request.getPatientSurname().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
