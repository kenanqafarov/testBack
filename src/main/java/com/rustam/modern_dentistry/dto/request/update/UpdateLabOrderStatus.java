package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dao.entity.enums.DentalWorkStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLabOrderStatus {
    Long id;
    DentalWorkStatus dentalWorkStatus;
}
