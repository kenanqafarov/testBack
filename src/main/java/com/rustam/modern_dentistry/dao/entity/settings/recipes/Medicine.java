package com.rustam.modern_dentistry.dao.entity.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicines")
@FieldDefaults(level = PRIVATE)
public class Medicine {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;
    String description;

    @Enumerated(STRING)
    Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "id", name = "recipe_id")
    Recipe recipe;

    @PrePersist
    void prePersist() {
        status = Status.ACTIVE;
    }
}
