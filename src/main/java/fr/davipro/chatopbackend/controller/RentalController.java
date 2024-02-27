package fr.davipro.chatopbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.dto.RentalsDTO;
import fr.davipro.chatopbackend.service.RentalService;

@RestController
public class RentalController {
    
    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public RentalsDTO getRentals() {
        RentalsDTO rentals = new RentalsDTO();
        List<RentalDTO> list = rentalService.getRentals();
        rentals.getRentals().addAll(list);
        return rentals;
    }
}