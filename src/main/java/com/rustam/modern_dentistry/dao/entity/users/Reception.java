package com.rustam.modern_dentistry.dao.entity.users;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "receptions")
@PrimaryKeyJoinColumn(name = "reception_id")
@DiscriminatorValue("RECEPTION")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reception extends BaseUser {
    String degree;
    @Column(name = "phone_2")
    String phone2;
    @Column(name = "phone_3")
    String phone3;
    @Column(name = "home_phone")
    String homePhone;
    String address;
    Integer experience;
}
