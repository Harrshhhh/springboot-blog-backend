package com.harsh.blog.services;

import com.harsh.blog.config.ModelMapping;
import com.harsh.blog.entities.User;
import com.harsh.blog.exceptions.ResourceNotFoundException;
import com.harsh.blog.payloads.UserDTO;
import com.harsh.blog.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        //Conversion of UserDTO to User
        User user = this.dtoToUser(userDTO);
        User savedUser = this.userRepo.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDTO updateUserById(UserDTO userDTO, Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDTO userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }


    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        return userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();

        List<UserDTO> userDTOS = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with id " + userId));
        userRepo.deleteById(userId);
    }

    private User dtoToUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        return user;
    }

    private UserDTO userToDto(User user) {
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
