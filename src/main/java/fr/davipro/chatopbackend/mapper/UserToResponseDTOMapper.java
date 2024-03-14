package fr.davipro.chatopbackend.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.dto.UserResponseDTO;
import fr.davipro.chatopbackend.model.User;

@Component
public class UserToResponseDTOMapper implements Function<User, UserResponseDTO> {

    @Override
    public UserResponseDTO apply(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreated_at(user.getCreatedAt());
        userDTO.setUpdated_at(user.getUpdatedAt());
        return userDTO;
    }
}