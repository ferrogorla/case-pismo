package com.pismo.service;

import com.pismo.dto.CreateUserDTO;
import com.pismo.dto.UserDTO;
import com.pismo.exception.AccountException;
import com.pismo.exception.UserException;
import com.pismo.mapper.UserMapper;
import com.pismo.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO create(CreateUserDTO createUserDTO) {
        if (Strings.isBlank(createUserDTO.getEmail()))
            throw new AccountException("Invalid email!");

        verifyIfUserExists(createUserDTO.getEmail());

        var user = userMapper.toEntity(createUserDTO);

        var savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    private void verifyIfUserExists(String email) {
        var userExists = userRepository.findByEmail(email);
        if (userExists.isPresent())
            throw new UserException("User " + email + " already exists!");
    }

}
