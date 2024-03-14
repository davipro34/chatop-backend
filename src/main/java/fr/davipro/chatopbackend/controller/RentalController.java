package fr.davipro.chatopbackend.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.dto.RentalsDTO;
import fr.davipro.chatopbackend.dto.UserDTO;
import fr.davipro.chatopbackend.service.RentalService;
import fr.davipro.chatopbackend.service.UserService;


@Tag(name = "Rental", description = "Rental management APIs")
@RestController
public class RentalController {

	private RentalService rentalService;
	private UserService userService;

	public RentalController(RentalService rentalService, UserService userService) {
		this.rentalService = rentalService;
		this.userService = userService;
	}

	@Operation(summary = "Retrieve a list of all rentals", description = "Get a list of rentals. The response is a list of Rental objects with an ID, name, surface, price, picture, description, ID owner, created date and updated date.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", description = "Rentals not found", content = {
			@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/rentals")
	public RentalsDTO getRentals() {
		RentalsDTO rentalsDTO = new RentalsDTO();
		List<RentalDTO> list = rentalService.getRentals();
		rentalsDTO.getRentals().addAll(list);
		return rentalsDTO;
	}

	@Operation(summary = "Retrieve a Rental by Id", description = "Get a Rental object by specifying its id. The response is Rental object with an ID, name, surface, price, picture, description, ID owner, created date and updated date.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", description = "Rental not found", content = {
			@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/rentals/{id}")
	public RentalDTO getRentalById(@PathVariable("id") final Integer id) {
		return rentalService.getRentalById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@Operation(summary = "Update a Rental by Id", description = "Update a Rental object by specifying its id. The request body should contain the details of the Rental to be updated.")
	@ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Rental updated", content = {
        	@Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }),
    	@ApiResponse(responseCode = "400", description = "Invalid input", content = {
        	@Content(schema = @Schema()) }),
    	@ApiResponse(responseCode = "404", description = "Rental not found", content = {
        	@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/rentals/{id}")
	public RentalDTO putRentalById(
		@PathVariable("id") final Integer id,
		@RequestParam("name") String name,
		@RequestParam("surface") BigDecimal surface,
		@RequestParam("price") BigDecimal price,
		@RequestParam("description") String description)
		{
		
		Optional<RentalDTO> existingRentalOptional = rentalService.getRentalById(id);
		if (!existingRentalOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found with id " + id);
		}
		RentalDTO existingRental = existingRentalOptional.get();
	
		existingRental.setName(name);
		existingRental.setSurface(surface);
		existingRental.setPrice(price);
		existingRental.setDescription(description);
	
		existingRental.setUpdated_at(LocalDateTime.now());
	
		return rentalService.updateRental(existingRental, id);
	}

	@Operation(summary = "Create a new rental", 
           description = "This operation creates a new rental with the provided details and returns the created rental.")
	@ApiResponses(value = { 
    	@ApiResponse(responseCode = "200", description = "Rental created successfully", content = { 
			@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class)) }),
    	@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content), 
    	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content), 
    	@ApiResponse(responseCode = "500", description = "Server error", content = @Content) })
	@PostMapping(value = "/rentals")
	public ResponseEntity<RentalDTO> postRental(
		@RequestParam("name") String name,
		@RequestParam("surface") BigDecimal surface,
		@RequestParam("price") BigDecimal price,
		@RequestParam("picture") MultipartFile picture,
		@RequestParam("description") String description,
		Authentication authentication
	) {
    	// Récupérer l'utilisateur actuellement authentifié
    	UserDTO currentUser = userService.getCurrentUser(authentication);

		// Créer un nouveau RentalDTO
		RentalDTO rentalDTO = new RentalDTO();
		rentalDTO.setName(name);
		rentalDTO.setSurface(surface);
		rentalDTO.setPrice(price);
		rentalDTO.setDescription(description);
		rentalDTO.setOwner_id(currentUser.getId());

		// Stocker l'image et obtenir son URL
		String pictureUrl = rentalService.storeFile(picture);
		rentalDTO.setPicture(pictureUrl);

		// Ajouter le nouveau Rental à la base de données
		RentalDTO newRental = rentalService.addRental(rentalDTO);

		// Retourner le nouveau Rental
		return ResponseEntity.ok(newRental);
	}
}