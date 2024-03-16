package fr.davipro.chatopbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.davipro.chatopbackend.dto.UserDTO;
import fr.davipro.chatopbackend.dto.UserResponseDTO;
import fr.davipro.chatopbackend.mapper.UserToDTOMapper;
import fr.davipro.chatopbackend.mapper.UserToResponseDTOMapper;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserToDTOMapper userToDTOMapper;

    @Autowired
    private UserToResponseDTOMapper userToResponseDTOMapper;

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("This email is already in use.");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userToDTOMapper.apply(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        return userToDTOMapper.apply(user);
    }

    public UserDTO getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserDTO userDTO = findByEmail(email);
        return userDTO;
    }

    public UserResponseDTO getCurrentUserResponse(Authentication authentication) {
        String email = authentication.getName();
        UserDTO userDTO = findByEmail(email);
        return userToResponseDTOMapper.applyFromDTO(userDTO);
    }
}