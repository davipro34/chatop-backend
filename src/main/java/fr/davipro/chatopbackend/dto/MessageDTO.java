package fr.davipro.chatopbackend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageDTO {
    private Integer id;
    private String message;
    private Integer user_id;
    private Integer rental_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
