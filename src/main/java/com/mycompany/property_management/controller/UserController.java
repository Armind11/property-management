package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.UserDTO;
import com.mycompany.property_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @Operation(description = "This Method is Used for User Registration")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Parameter(
            name = "UserDTO",
            example = "user Information",
            required = true
    ) @Valid @RequestBody() UserDTO userDTO) {

        userDTO = userService.register(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO userDTO) {
        userDTO = userService.login(userDTO.getOwnerEmail(),userDTO.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
