package com.rustam.modern_dentistry.dao.entity;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
@Table(name = "garnitures ")
@FieldDefaults(level = PRIVATE)
public class Garniture {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;

    @Enumerated(STRING)
    Status status;

    @PrePersist
    void prePersist() {
        status = ACTIVE;
    }
}
