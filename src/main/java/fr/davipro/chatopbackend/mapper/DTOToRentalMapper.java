package fr.davipro.chatopbackend.mapper;

import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.model.Rental;

@Component
public class DTOToRentalMapper {

    public Rental createNew(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setCreatedAt(rentalDTO.getCreated_at());
        rental.setUpdatedAt(rentalDTO.getUpdated_at());


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
        if (rentalDTO.getCreated_at() != null) {
            rental.setCreatedAt(rentalDTO.getCreated_at());
        }
        rental.setUpdatedAt(rentalDTO.getUpdated_at());
        return rental;
    }

}