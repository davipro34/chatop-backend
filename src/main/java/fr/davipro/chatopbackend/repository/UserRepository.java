package fr.davipro.chatopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.davipro.chatopbackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
