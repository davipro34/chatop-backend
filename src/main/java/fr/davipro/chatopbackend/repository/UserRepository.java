package fr.davipro.chatopbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.davipro.chatopbackend.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}
