package com.pismo.mapper;

import com.pismo.dto.CreateUserDTO;
import com.pismo.dto.UserDTO;
import com.pismo.model.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public User toEntity(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        return user;
    }

}
