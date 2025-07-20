package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.BlacklistResult;
import com.rustam.modern_dentistry.dto.request.read.BlacklistResultSearchReq;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class BlacklistResultSpecification {
    public static Specification<BlacklistResult> filterBy(BlacklistResultSearchReq request) {

        return new SpecificationBuilder<BlacklistResult>()
                .addLike("statusName", request.getStatusName())
                .addEqual("status", request.getStatus())
                .build();
    }
}
