package com.rustam.modern_dentistry.dao.entity.patient_info;

import com.rustam.modern_dentistry.dao.entity.users.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_anamnesis ")
@FieldDefaults(level = PRIVATE)
public class PatientAnamnesis {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(nullable = false)
    String anamnesisCategoryName;

    @Column(nullable = false)
    String anamnesisName;

    @Column(nullable = false)
    String addedByName;

    @CreationTimestamp
    LocalDateTime addedDateTime;

    @ManyToOne(fetch =  LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    Patient patient;
}
