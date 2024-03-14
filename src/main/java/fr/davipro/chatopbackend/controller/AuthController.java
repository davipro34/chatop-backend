package fr.davipro.chatopbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.davipro.chatopbackend.service.JWTService;
import fr.davipro.chatopbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import fr.davipro.chatopbackend.dto.JwtResponseDTO;
import fr.davipro.chatopbackend.dto.UserDTO;
import fr.davipro.chatopbackend.model.User;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Operation(summary = "Register a new user", description = "This operation registers a new user with the provided details and returns the registered user.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User registered successfully", content = { 
            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content), 
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), 
    @ApiResponse(responseCode = "500", description = "Server error", content = @Content) })
    @PostMapping("/auth/register")
    public ResponseEntity<JwtResponseDTO> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(registeredUser.getEmail(), registeredUser.getPassword());
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(JwtResponseDTO.builder().token(token).build());
    }
    
    @Operation(summary = "Log in a user", description = "This operation logs in a user with the provided details and returns a token.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User logged in successfully", content = { 
            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class)) }),
        @ApiResponse(responseCode = "401", description = "Invalid login credentials", content = @Content), 
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid UserDTO userDto) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword()));
    
            String token = jwtService.generateToken(authenticate);
            logger.info("Token is : " + token);
    
            return ResponseEntity.ok(JwtResponseDTO.builder().token(token).build());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Get current user", description = "This operation returns the details of the currently authenticated user.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User details retrieved successfully", content = { 
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), 
    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
    @GetMapping("/auth/me")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(userDTO);
    }
}