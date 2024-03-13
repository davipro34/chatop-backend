package fr.davipro.chatopbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.davipro.chatopbackend.dto.UserDTO;
import fr.davipro.chatopbackend.mapper.UserToDTOMapper;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserToDTOMapper userToDTOMapper;

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

    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("This email is already in use.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }

    public UserDTO getCurrentUser(Authentication authentication) {
    String email = authentication.getName();
    User user = findByEmail(email);
    return userToDTOMapper.apply(user);
    }

    public UserDTO getCurrentUserByEmail(String email) {
        User user = findByEmail(email);
        return userToDTOMapper.apply(user);
    }
}