package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "room_stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    
}
