package fr.davipro.chatopbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.mapper.RentalToDTOMapper;
import fr.davipro.chatopbackend.model.Rental;
import fr.davipro.chatopbackend.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalToDTOMapper rentalToDTOMapper;

    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalDTO> getRentals() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalsDTO = new ArrayList<>();
        for (Rental rental : rentals) {
            RentalDTO rentalDTO = rentalToDTOMapper.apply(rental);
            rentalsDTO.add(rentalDTO);
        }
        return rentalsDTO;
    }

    public Optional<RentalDTO> getRentalById(Integer id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if(rental.isPresent()) {
            RentalDTO rentalDTO = rentalToDTOMapper.apply(rental.get());
            return Optional.of(rentalDTO);
        } else {
            return Optional.empty();
        }
    }
}
