package com.harsh.blog.controllers;

import com.harsh.blog.payloads.UserDTO;
import com.harsh.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createUserDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserByID(@RequestBody UserDTO userDTO, @PathVariable Long userId){
        UserDTO updatedUserById = this.userService.updateUserById(userDTO, userId);
        return ResponseEntity.ok(updatedUserById);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable Long userId){
        UserDTO userDTO =  this.userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity(Map.of("message","User deleted successfully."),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers(); // fetch users from service
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 No Content if list is empty
        }
        return ResponseEntity.ok(users);  // 200 OK with the list of users
    }

}
