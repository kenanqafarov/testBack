package com.rustam.modern_dentistry.dao.entity.users;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "technician")
@DiscriminatorValue("TECHNICIAN")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "technician_id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Technician extends BaseUser {
    String phone2;
    String homePhone;
    String address;
    @Enumerated(EnumType.STRING)
    Status status;
    //    List<Permission> permissions

    @PrePersist
    private void prePersist() {
        status = Status.ACTIVE;
    }
}
