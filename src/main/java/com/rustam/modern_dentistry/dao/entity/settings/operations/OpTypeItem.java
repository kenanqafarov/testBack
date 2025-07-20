package com.rustam.modern_dentistry.dao.entity.settings.operations;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "op_type_items")
@FieldDefaults(level = PRIVATE)
public class OpTypeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String operationName;
    String operationCode;
    Status status;
    boolean showTechnic;

    @OneToMany(mappedBy = "opTypeItem", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OpTypeItemPrice> prices;

    @OneToMany(mappedBy = "opTypeItem", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OpTypeItemInsurance> insurances;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "op_type_id", referencedColumnName = "id")
    OpType opType;
}
