package fr.davipro.chatopbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

	// @Operation(summary = "Create a new Rental", description = "Create a new Rental object. The request should contain a RentalDTO object. The response is the created RentalDTO object with an ID, name, surface, price, picture, description, ID owner, created date and updated date.")
	// @ApiResponses({
	// 	@ApiResponse(responseCode = "201", content = {
	// 		@Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }),
	// 	@ApiResponse(responseCode = "400", description = "Invalid RentalDTO provided", content = {
	// 		@Content(schema = @Schema()) }),
	// 	@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	// @PostMapping("/api/rentals")
	// @RequestMapping(value = "/rentals", headers = "Accept=application/json",method = RequestMethod.PUT)
	// public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
	// 	RentalDTO createdRentalDTO = rentalService.createRental(rentalDTO);
	// 	return new ResponseEntity<>(createdRentalDTO, HttpStatus.CREATED);
	// }
}