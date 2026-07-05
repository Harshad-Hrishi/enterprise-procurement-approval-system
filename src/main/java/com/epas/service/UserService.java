package com.epas.service;


import com.epas.dto.request.UserRequestDTO;
import com.epas.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO create(UserRequestDTO request);

//    UserResponseDTO update(UUID id, UserRequestDTO request);

    UserResponseDTO getById(UUID id);

    List<UserResponseDTO> getAll();

    void delete(UUID id);

}