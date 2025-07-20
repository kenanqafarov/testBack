package com.rustam.modern_dentistry.util.specification.settings;


import com.rustam.modern_dentistry.dao.entity.PatientBlacklist;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.request.read.PatBlacklistSearchReq;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PatientBlacklistSpecification {

    public static Specification<PatientBlacklist> search(PatBlacklistSearchReq req) {
        return (root, query, cb) -> {
            // Join with Patient entity
            Join<PatientBlacklist, Patient> patientJoin = root.join("patient", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(req.getFullName())) {
                String fullName = URLDecoder.decode(req.getFullName(), StandardCharsets.UTF_8)
                        .trim()
                        .replaceAll("\\s+", " "); // Multiple spaces to single space

                String[] nameParts = fullName.split(" ", 2); // Split into max 2 parts

                if (nameParts.length > 1) {
                    // First part for name, second part for surname
                    Predicate namePredicate = cb.like(
                            cb.lower(patientJoin.get("name")),
                            "%" + nameParts[0].toLowerCase() + "%"
                    );
                    Predicate surnamePredicate = cb.like(
                            cb.lower(patientJoin.get("surname")),
                            "%" + nameParts[1].toLowerCase() + "%"
                    );
                    predicates.add(cb.and(namePredicate, surnamePredicate));
                } else {
                    // Single word - search in both name and surname
                    Predicate namePredicate = cb.like(
                            cb.lower(patientJoin.get("name")),
                            "%" + fullName.toLowerCase() + "%"
                    );
                    Predicate surnamePredicate = cb.like(
                            cb.lower(patientJoin.get("surname")),
                            "%" + fullName.toLowerCase() + "%"
                    );
                    predicates.add(cb.or(namePredicate, surnamePredicate));
                }
            }

            if (req.getFinCode() != null && !req.getFinCode().isBlank()) {
                predicates.add(cb.like(cb.lower(patientJoin.get("finCode")), "%" + req.getFinCode().toLowerCase() + "%"));
            }

            if (req.getMobilePhone() != null && !req.getMobilePhone().isBlank()) {
                predicates.add(cb.like(cb.lower(patientJoin.get("phone")), "%" + req.getMobilePhone().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
