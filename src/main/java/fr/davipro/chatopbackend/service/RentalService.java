package fr.davipro.chatopbackend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import fr.davipro.chatopbackend.dto.RentalDTO;
import fr.davipro.chatopbackend.mapper.DTOToRentalMapper;
import fr.davipro.chatopbackend.mapper.RentalToDTOMapper;
import fr.davipro.chatopbackend.model.Rental;
import fr.davipro.chatopbackend.model.User;
import fr.davipro.chatopbackend.repository.RentalRepository;
import fr.davipro.chatopbackend.repository.UserRepository;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class RentalService {

    @Autowired
    private RentalToDTOMapper rentalToDTOMapper;

    @Autowired
    private DTOToRentalMapper dtoToRentalMapper;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Cloudinary cloudinary;

    public List<RentalDTO> getRentals() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalsDTO = new ArrayList<>();
        for (Rental rental : rentals) {
            RentalDTO rentalDTO = rentalToDTOMapper.apply(rental);
            rentalsDTO.add(rentalDTO);
        }
        return rentalsDTO;
    }

    public Optional<RentalDTO> getRentalById(Integer id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if(rental.isPresent()) {
            RentalDTO rentalDTO = rentalToDTOMapper.apply(rental.get());
            return Optional.of(rentalDTO);
        } else {
            return Optional.empty();
        }
    }
    
    @Transactional
    public RentalDTO addRental(RentalDTO rentalDTO) {

        // Convert DTO to entity
        Rental rental = dtoToRentalMapper.apply(rentalDTO);
    
        // Verify if proprietary's ID is null
        if (rentalDTO.getOwnerId() == null) {
            // Define a fix value "1" for ownerId (for tests before token implementation)
            rentalDTO.setOwnerId(1);
        }
    
        // Fetch user
        Optional<User> ownerOptional = userRepository.findById(rentalDTO.getOwnerId());
        if (ownerOptional.isPresent()) {
            rental.setOwner(ownerOptional.get());
        } else {
            throw new RuntimeException("User not found with id " + rentalDTO.getOwnerId());
        }
    
        // Persist entity
        Rental savedRental = saveOrUpdate(rental);
    
        // Convert the registered entity to DTO
        RentalDTO savedRentalDTO = rentalToDTOMapper.apply(savedRental);
    
        return savedRentalDTO;
    }

    // Here we use EntityManager to persist or update the owner in the same hibernate session as the persistence of the rental.
    @Transactional
    public Rental saveOrUpdate(Rental rental) {
        if (rental.getId() == null) {
            entityManager.persist(rental);
        } else {
            rental = entityManager.merge(rental);
        }
        return rental;
    }

    public String storeFile(MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
