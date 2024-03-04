package fr.davipro.chatopbackend.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.model.Rental;

@Component
public class RentalToDTOMapper implements Function<Rental, RentalDTO> {

    @Override
    public RentalDTO apply(Rental rental) {
        return RentalDTO.builder()
            .id(rental.getId())
            .name(rental.getName())
            .surface(rental.getSurface())
            .price(rental.getPrice())
            .picture(rental.getPicture())
            .description(rental.getDescription())
            .ownerId(rental.getOwner().getId())
            .createdAt(rental.getCreatedAt())
            .updatedAt(rental.getUpdatedAt())
            .build();
    }
}
