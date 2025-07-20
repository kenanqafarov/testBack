package com.rustam.modern_dentistry.dao.entity.settings;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeInsurance;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItemInsurance;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "insurance_companies")
@FieldDefaults(level = PRIVATE)
public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(nullable = false)
    String companyName;
    BigDecimal deductibleAmount;

    @Enumerated(STRING)
    Status status;

    @OneToMany(mappedBy = "insuranceCompany", cascade = CascadeType.REMOVE)
    List<OpTypeInsurance> insurance;

    @OneToMany(mappedBy = "insuranceCompany", cascade = CascadeType.REMOVE)
    List<OpTypeItemInsurance> insuranceItem;
}
