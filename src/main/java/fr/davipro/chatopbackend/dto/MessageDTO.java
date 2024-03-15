package fr.davipro.chatopbackend.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String message;
    private Integer user_id;
    private Integer rental_id;
}
