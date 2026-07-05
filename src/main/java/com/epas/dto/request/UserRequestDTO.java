package com.epas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String password;

    private UUID departmentId;

    private String designation;

    private Boolean enabled;
}
