package com.epas.mapper;

import com.epas.dto.request.UserRequestDTO;
import com.epas.dto.response.UserResponseDTO;
import com.epas.entity.Department;
import com.epas.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto, Department department) {

        if (dto == null) {
            return null;
        }

        return User.builder()
                .employeeCode(dto.getEmployeeCode())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .password(dto.getPassword())   // Will be encoded in Service
                .department(department)
                .designation(dto.getDesignation())
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : true)
                .build();
    }

    public UserResponseDTO toResponse(User user) {

        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .employeeCode(user.getEmployeeCode())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .designation(user.getDesignation())
                .departmentName(
                        user.getDepartment() != null
                                ? user.getDepartment().getName()
                                : null
                )
                .enabled(user.getEnabled())
                .accountNonLocked(user.getAccountNonLocked())
                .lastLogin(user.getLastLogin())
                .build();
    }
}