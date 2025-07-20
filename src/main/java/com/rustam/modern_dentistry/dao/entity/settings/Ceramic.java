package com.rustam.modern_dentistry.dao.entity.settings;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import static com.rustam.modern_dentistry.dao.entity.enums.status.Status.ACTIVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ceramics")
@FieldNameConstants(level = PRIVATE)
public class Ceramic {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Enumerated(STRING)
    Status status;

    @PrePersist
    void prePersist() {
        status = ACTIVE;
    }
}
