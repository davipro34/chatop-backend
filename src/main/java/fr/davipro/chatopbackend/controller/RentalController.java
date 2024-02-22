package fr.davipro.chatopbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import fr.davipro.chatopbackend.service.RentalService;
import fr.davipro.chatopbackend.model.Rental;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/api/rentals")
    public Iterable<Rental> getRentals() {
        return rentalService.getRentals();
    }
}
