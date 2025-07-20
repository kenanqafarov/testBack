package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.users.Technician;
import com.rustam.modern_dentistry.dto.request.read.TechnicianSearchRequest;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class TechnicianSpecification {
    public static Specification<Technician> filterBy(TechnicianSearchRequest request) {

        return new SpecificationBuilder<Technician>()
                .addLike("username", request.getUsername())
                .addLike("name", request.getName())
                .addLike("surname", request.getSurname())
                .addLike("patronymic", request.getPatronymic())
                .addLike("phone", request.getPhone())
                .addLike("finCode", request.getFinCode())
                .addEqual("status", request.getStatus())
                .build();
    }
}
