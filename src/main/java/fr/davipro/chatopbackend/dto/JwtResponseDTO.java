package fr.davipro.chatopbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDTO {
    private String token;
}
