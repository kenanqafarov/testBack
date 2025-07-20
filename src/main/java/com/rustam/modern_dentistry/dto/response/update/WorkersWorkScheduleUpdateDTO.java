package com.rustam.modern_dentistry.dto.response.update;

import com.rustam.modern_dentistry.dao.entity.enums.WeekDay;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkersWorkScheduleUpdateDTO {

    Long id;

    WeekDay weekDay;

    Room room;

    LocalTime startTime;

    LocalTime finishTime;
}
