package com.rustam.modern_dentistry.dao.entity.settings;

import com.rustam.modern_dentistry.dao.entity.PatientBlacklist;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blacklist_results")
@FieldDefaults(level = PRIVATE)
public class BlacklistResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String statusName;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "blacklistResult")
    List<PatientBlacklist> patientBlacklist;

    @PrePersist
    void prePersist() {
        status = ACTIVE;
    }
}
