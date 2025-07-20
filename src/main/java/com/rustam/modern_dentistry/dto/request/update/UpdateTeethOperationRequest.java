package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dto.request.create.OpTypeAndItemRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTeethOperationRequest {

    Long id;
    Long teethId;
    List<OpTypeAndItemRequest> opTypeAndItemRequests;
}
