package fr.davipro.chatopbackend.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    System.out.println("User not found with email : " + username);
                    return new UsernameNotFoundException("User not found with email : " + username);
                });

        System.out.println("User found with email : " + username); // Utilisateur trouvé

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password("{bcrypt}" + user.getPassword()) // Ajoutez le préfixe {bcrypt} ici
                .authorities(new ArrayList<>()) // Vous pouvez ajouter des autorités si vous en avez
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}