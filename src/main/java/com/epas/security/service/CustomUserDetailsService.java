package com.epas.security.service;

import com.epas.entity.RolePermission;
import com.epas.entity.User;
import com.epas.entity.UserRole;
import com.epas.repository.RolePermissionRepository;
import com.epas.repository.UserRepository;
import com.epas.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities = new HashSet<>();

        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        for (UserRole userRole : userRoles) {

            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + userRole.getRole().getCode()
                    )
            );

            List<RolePermission> rolePermissions =
                    rolePermissionRepository.findByRole(userRole.getRole());

            for (RolePermission rolePermission : rolePermissions) {

                authorities.add(
                        new SimpleGrantedAuthority(
                                rolePermission.getPermission().getCode()
                        )
                );
            }
        }

        return new CustomUserDetails(user, authorities);
    }
}