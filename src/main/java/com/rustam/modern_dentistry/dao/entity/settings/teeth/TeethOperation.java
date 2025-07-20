package com.rustam.modern_dentistry.dao.entity.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "teeth_operation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeethOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tooth_id", nullable = false)
    Teeth teeth;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "op_type_item_id")
    OpTypeItem opTypeItem;
}
