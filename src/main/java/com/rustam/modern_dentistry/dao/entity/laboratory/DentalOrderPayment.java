package com.rustam.modern_dentistry.dao.entity.laboratory;

import com.rustam.modern_dentistry.dao.entity.users.Technician;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dental_order_payment")
public class DentalOrderPayment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    LocalDate paymentDate;
    BigDecimal amount;     // Ödənilən məbləğ

    @ManyToOne
    @JoinColumn(name = "technician_id")
    Technician technician;
}
