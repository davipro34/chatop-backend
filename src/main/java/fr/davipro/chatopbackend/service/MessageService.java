package fr.davipro.chatopbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.davipro.chatopbackend.dto.MessageDTO;
import fr.davipro.chatopbackend.mapper.DTOToMessageMapper;
import fr.davipro.chatopbackend.model.Message;
import fr.davipro.chatopbackend.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DTOToMessageMapper dtoToMessageMapper;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void addMessage(MessageDTO messageDTO) {
        Message message = dtoToMessageMapper.mapToMessage(messageDTO);
        saveMessage(message);
    }
}
