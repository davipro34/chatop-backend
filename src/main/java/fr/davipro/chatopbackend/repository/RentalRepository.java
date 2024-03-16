package fr.davipro.chatopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.davipro.chatopbackend.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer>{
}
