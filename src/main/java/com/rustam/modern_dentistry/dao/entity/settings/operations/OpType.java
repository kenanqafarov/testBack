package com.rustam.modern_dentistry.dao.entity.settings.operations;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "op_types")
@FieldDefaults(level = PRIVATE)
public class OpType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String categoryName;

    boolean colorSelection;
    boolean implantSelection;

    @Enumerated(STRING)
    Status status;

    @OneToMany(mappedBy = "opType", cascade = ALL, fetch = LAZY, orphanRemoval = true)
    List<OpTypeInsurance> insurances;

    @OneToMany(mappedBy = "opType", cascade = ALL)
    List<OpTypeItem> opTypeItems;
}
