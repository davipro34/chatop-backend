package fr.davipro.chatopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.davipro.chatopbackend.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
}
