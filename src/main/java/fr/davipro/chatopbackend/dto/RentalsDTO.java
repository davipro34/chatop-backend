package fr.davipro.chatopbackend.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RentalsDTO {

    private List<RentalDTO> rentals = new ArrayList<>();
}
