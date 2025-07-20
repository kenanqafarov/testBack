package com.rustam.modern_dentistry.util.specification;

import com.rustam.modern_dentistry.dao.entity.Reservation;
import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.request.read.ReservationSearchRequest;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ReservationSpec implements Specification<Reservation> {

    private ReservationSearchRequest cr;

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Join<Reservation, Patient> patientJoin = root.join("Patient", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        System.out.println("patientJoin.getAttribute().getName()" + patientJoin.getAttribute().getName());

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
