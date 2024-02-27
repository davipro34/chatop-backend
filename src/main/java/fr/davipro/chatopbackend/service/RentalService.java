package fr.davipro.chatopbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.model.Rental;
import fr.davipro.chatopbackend.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalDTO> getRentals() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalsDTOs = new ArrayList<>();
        for (Rental rental : rentals) {
            RentalDTO rentalDTO = convert(rental);
            rentalsDTOs.add(rentalDTO);
        }
        return rentalsDTOs;
    }

    public Optional<Rental> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRentalById(Integer id) {
        rentalRepository.deleteById(id);
    }

    private RentalDTO convert(Rental entity) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(entity.getId());
        rentalDTO.setName(entity.getName());
        rentalDTO.setSurface(entity.getSurface());
        rentalDTO.setPrice(entity.getPrice());
        rentalDTO.setPicture(entity.getPicture());
        rentalDTO.setDescription(entity.getDescription());
        rentalDTO.setOwner_id(entity.getOwner().getId());
        rentalDTO.setCreated_at(entity.getCreated_at());
        rentalDTO.setUpdated_at(entity.getUpdated_at());
        return rentalDTO;
    }
}
