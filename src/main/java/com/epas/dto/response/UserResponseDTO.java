package com.epas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String designation;

    private String departmentName;

    private Boolean enabled;

    private Boolean accountNonLocked;

    private LocalDateTime lastLogin;
}