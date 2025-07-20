package com.rustam.modern_dentistry.dao.entity.users;

import com.rustam.modern_dentistry.dao.entity.enums.status.GenderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "admin_id")
@DiscriminatorValue("ADMIN")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends BaseUser{
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
