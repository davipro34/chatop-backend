package fr.davipro.chatopbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.davipro.chatopbackend.model.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer>{

}
