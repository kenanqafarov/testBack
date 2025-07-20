package com.rustam.modern_dentistry.util.user;

import com.rustam.modern_dentistry.dao.entity.enums.PermissionAction;
import com.rustam.modern_dentistry.dao.entity.settings.permission.Permission;
import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


public record CustomUserDetails(BaseUser user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        boolean isSuperAdmin = user.getPermissions().stream()
                .anyMatch(p -> "SUPER_ADMIN".equals(p.getPermissionName()));

        if (isSuperAdmin) {
            authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
            return authorities;
        }

        user.getPermissions().forEach(permission ->
                permission.getModulePermissions().forEach(module ->
                        module.getActions().forEach(action -> {
                            String auth = module.getModuleUrl() + ":" + action.name();
                            authorities.add(new SimpleGrantedAuthority(auth));
                        })
                )
        );

        return authorities;
    }




    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
