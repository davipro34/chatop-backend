package fr.davipro.chatopbackend.mapper;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.model.Rental;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.service.UserService;

@Component
public class DTOToRentalMapper {

    private final UserService userService;

    public DTOToRentalMapper(UserService userService) {
        this.userService = userService;
    }

    public Rental createNew(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setCreatedAt(rentalDTO.getCreatedAt());
        rental.setUpdatedAt(rentalDTO.getUpdatedAt());


        // Code original pour définir le propriétaire de la location en fonction de rentalDTO.getOwnerId()
        // Décommentez ce code lorsque je serai prêt à utiliser l'authentification par token
        /*
        Optional<User> ownerOptional = userService.getUserById(rentalDTO.getOwnerId());
        if (ownerOptional.isPresent()) {
            rental.setOwner(ownerOptional.get());
        } else {
            throw new RuntimeException("User not found with id " + rentalDTO.getOwnerId());
        }
        */
    
        return rental;
    }

    public Rental updateExisting(RentalDTO rentalDTO, Rental rental) {
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        if (rentalDTO.getPicture() != null) {
            rental.setPicture(rentalDTO.getPicture());
        }
        rental.setDescription(rentalDTO.getDescription());
        if (rentalDTO.getCreatedAt() != null) {
            rental.setCreatedAt(rentalDTO.getCreatedAt());
        }
        rental.setUpdatedAt(rentalDTO.getUpdatedAt());
        return rental;
    }

}