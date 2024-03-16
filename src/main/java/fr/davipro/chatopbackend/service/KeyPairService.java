package fr.davipro.chatopbackend.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class KeyPairService {

    private KeyPair keyPair;

    @PostConstruct
    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    @PreDestroy
    public void destroyKeyPair() {
        keyPair = null;
    }
}
