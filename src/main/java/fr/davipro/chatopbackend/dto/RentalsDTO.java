package fr.davipro.chatopbackend.dto;

import java.util.ArrayList;
import java.util.List;

public class RentalsDTO {

    private List<RentalDTO> rentals = new ArrayList<>();

    public List<RentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }
}
