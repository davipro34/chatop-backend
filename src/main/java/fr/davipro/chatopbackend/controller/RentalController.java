package fr.davipro.chatopbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.dto.RentalsDTO;
import fr.davipro.chatopbackend.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rental", description = "Rental management APIs")
@RestController
public class RentalController {
    
    @Autowired
    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
      this.rentalService = rentalService;
    }

    @Operation(
      summary = "Retrieve a list of all rentals",
      description = "Get a list of rentals. The response is a list of Rental objects with an ID, name, surface, price, picture, description, ID owner, created date and updated date.")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/api/rentals")
    public RentalsDTO getRentals() {
        RentalsDTO rentals = new RentalsDTO();
        List<RentalDTO> list = rentalService.getRentals();
        rentals.getRentals().addAll(list);
        return rentals;
    }

    @Operation(
      summary = "Retrieve a Rental by Id",
      description = "Get a Rental object by specifying its id. The response is Rental object with an ID, name, surface, price, picture, description, ID owner, created date and updated date.")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/api/rentals/{id}")
    public Optional<RentalDTO> getRentalById(@PathVariable Integer id) {
        return rentalService.getRentalById(id);
    }
}