package com.epas.repository;

import com.epas.entity.Role;
import com.epas.entity.User;
import com.epas.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    List<UserRole> findByUser(User user);

    List<UserRole> findByRole(Role role);

}