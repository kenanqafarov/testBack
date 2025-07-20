package com.rustam.modern_dentistry.dao.entity.settings.implant;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "implant")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Implant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "implant_brand_name")
    String implantBrandName;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "implant",cascade = CascadeType.ALL,orphanRemoval = true)
    List<ImplantSizes> implantSizes;
}
