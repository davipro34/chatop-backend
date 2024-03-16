package fr.davipro.chatopbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.davipro.chatopbackend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import fr.davipro.chatopbackend.dto.MessageDTO;

@Tag(name = "Message", description = "Message management endpoints")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Post a new message", description = "This endpoint allows a user to post a new message.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message posted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Server error")})
    @PostMapping(value = "/messages")
    public String postMessage(@RequestBody MessageDTO messageDTO) {
        messageService.addMessage(messageDTO);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("message", "Message send with success");

        String formattedResponse = "";
        try {
            formattedResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return formattedResponse;
    }
}