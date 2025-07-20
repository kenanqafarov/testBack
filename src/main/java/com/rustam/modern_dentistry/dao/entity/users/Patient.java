package com.rustam.modern_dentistry.dao.entity.users;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustam.modern_dentistry.dao.entity.GeneralCalendar;
import com.rustam.modern_dentistry.dao.entity.PatientBlacklist;
import com.rustam.modern_dentistry.dao.entity.Reservation;
import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.PriceCategoryStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.SpecializationStatus;
import com.rustam.modern_dentistry.dao.entity.patient_info.PatientExaminations;
import com.rustam.modern_dentistry.dao.entity.patient_info.PatientPhotos;
import com.rustam.modern_dentistry.dao.entity.patient_info.PatientRecipe;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String patronymic;
    @Column(unique = true, name = "fin_code")
    String finCode;
    @Column(name = "gender_status")
    GenderStatus genderStatus;
    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    Doctor doctor;
    @Column(name = "price_category_status")
    PriceCategoryStatus priceCategoryStatus;
    @Column(name = "specialication_status")
    SpecializationStatus specializationStatus;
    String phone;
    String email;
    Boolean enabled;
    @Column(name = "work_phone")
    String workPhone;
    @Column(name = "home_phone")
    String homePhone;
    @Column(name = "home_address")
    String homeAddress;
    @Column(name = "work_address")
    String workAddress;
    LocalDate registration_date;

    @OneToMany(mappedBy = "patient", cascade = ALL, fetch = LAZY)
    List<Reservation> reservations;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<GeneralCalendar> generalCalendars;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PatientExaminations> examinations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<PatientPhotos> photos;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<PatientPhotos> videos;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<PatientRecipe> recipes;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    PatientBlacklist patientBlacklist;
}
