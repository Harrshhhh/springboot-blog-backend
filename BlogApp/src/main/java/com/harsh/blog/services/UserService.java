package com.harsh.blog.services;

import com.harsh.blog.payloads.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUserById(UserDTO userDTO, Long userId);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);

}
