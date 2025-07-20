package com.rustam.modern_dentistry.dao.entity.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "op_type_item_insurances")
@FieldDefaults(level = PRIVATE)
public class OpTypeItemInsurance {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;
    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "op_type_item_id", nullable = false)
    OpTypeItem opTypeItem;

    @ManyToOne
    @JoinColumn(name = "insurance_id", nullable = false)
    InsuranceCompany insuranceCompany;
}
