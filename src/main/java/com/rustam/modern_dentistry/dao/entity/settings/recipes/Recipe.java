package com.rustam.modern_dentistry.dao.entity.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.patient_info.PatientRecipe;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes")
@FieldDefaults(level = PRIVATE)
public class Recipe {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Enumerated(STRING)
    Status status;

    @OneToMany(mappedBy = "recipe", cascade = ALL)
    List<Medicine> medicines;

    @OneToMany(mappedBy = "recipe", cascade = ALL)
    List<PatientRecipe> recipes;

    @PrePersist
    void prePersist() {
        status = Status.ACTIVE;
    }
}
