package com.pismo.controller;

import com.pismo.dto.CreateUserDTO;
import com.pismo.dto.UserDTO;
import com.pismo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Api(tags = "Operações de usuário")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Criar usuário")
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(createUserDTO));
    }

}
