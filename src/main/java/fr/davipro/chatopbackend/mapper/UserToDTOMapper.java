package fr.davipro.chatopbackend.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.dto.UserDTO;
import fr.davipro.chatopbackend.model.User;

@Component
public class UserToDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setLogin(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreated_at(user.getCreatedAt());
        userDTO.setUpdated_at(user.getUpdatedAt());
        return userDTO;
    }
}