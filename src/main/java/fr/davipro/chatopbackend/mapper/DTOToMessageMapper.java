package fr.davipro.chatopbackend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.model.Message;
import fr.davipro.chatopbackend.dto.MessageDTO;
import fr.davipro.chatopbackend.repository.RentalRepository;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.model.Rental;

import java.util.Optional;

@Component
public class DTOToMessageMapper {

    @Autowired
    private RentalRepository rentalRepository;

    public Message mapToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());

        // Trouver la location par son ID
        Optional<Rental> rental = rentalRepository.findById(messageDTO.getRental_id());

        // Si la location est présente, définir la location du message
        if (rental.isPresent()) {
            Rental newRental = rental.get();
            message.setRental(newRental);

            // Utiliser l'ID du propriétaire de la location
            User owner = newRental.getOwner();
            message.setUser(owner);
        }

        return message;
    }
}