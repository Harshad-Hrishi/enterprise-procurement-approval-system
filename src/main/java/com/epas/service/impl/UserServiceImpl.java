package com.epas.service.impl;

import com.epas.dto.request.UserRequestDTO;
import com.epas.dto.response.UserResponseDTO;
import com.epas.entity.Department;
import com.epas.entity.User;
import com.epas.mapper.UserMapper;
import com.epas.repository.DepartmentRepository;
import com.epas.repository.UserRepository;
import com.epas.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {

        // Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Check duplicate employee code
        if (userRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists.");
        }

        // Fetch department
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found."));

        // Convert DTO to Entity
        User user = userMapper.toEntity(request, department);

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save
        User savedUser = userRepository.save(user);

        // Return response
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional()
    public UserResponseDTO getById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional()
    public List<UserResponseDTO> getAll() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setActive(false);

        userRepository.save(user);
    }
}