package fr.davipro.chatopbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.davipro.chatopbackend.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{

}
