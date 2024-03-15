package fr.davipro.chatopbackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.model.Message;
import fr.davipro.chatopbackend.dto.MessageDTO;
import fr.davipro.chatopbackend.repository.UserRepository;
import fr.davipro.chatopbackend.repository.RentalRepository;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.model.Rental;

import java.util.Optional;

@Component
public class DTOToMessageMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public Message mapToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());

        Optional<User> user = userRepository.findById(messageDTO.getUser_id());
        user.ifPresent(message::setUser);

        Optional<Rental> rental = rentalRepository.findById(messageDTO.getRental_id());
        rental.ifPresent(message::setRental);

        return message;
    }
}