package com.rustam.modern_dentistry.dao.entity;

import com.rustam.modern_dentistry.dao.entity.enums.WeekDay;
import com.rustam.modern_dentistry.dao.entity.enums.status.ReservationStatus;
import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations ")
@FieldDefaults(level = PRIVATE)
public class Reservation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;

    @Column(name = "week_day")
    @Enumerated(STRING)
    @ElementCollection(targetClass = WeekDay.class)
    @CollectionTable(name = "reservation_weekdays", joinColumns = @JoinColumn(name = "reservation_id"))
    Set<WeekDay> weekDays;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    Doctor doctor;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    Patient patient;

    @Enumerated(STRING)
    ReservationStatus status;
}
