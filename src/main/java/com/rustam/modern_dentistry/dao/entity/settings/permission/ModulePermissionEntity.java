package com.rustam.modern_dentistry.dao.entity.settings.permission;

import com.rustam.modern_dentistry.dao.entity.enums.PermissionAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "module_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModulePermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "module_url")
    String moduleUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "module_permission_actions", joinColumns = @JoinColumn(name = "module_permission_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    Set<PermissionAction> actions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    Permission permission;
}

