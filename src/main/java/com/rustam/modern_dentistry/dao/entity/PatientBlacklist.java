package com.rustam.modern_dentistry.dao.entity;

import com.rustam.modern_dentistry.dao.entity.settings.BlacklistResult;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_blacklists")
@FieldDefaults(level = PRIVATE)
public class PatientBlacklist {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    LocalDate createdDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "blacklist_result_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    BlacklistResult blacklistResult;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "patient_id")
    Patient patient;

    @PrePersist
    void prePersist() {
        createdDate = LocalDate.now();
    }
}
