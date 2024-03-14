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
import fr.davipro.chatopbackend.repository.RentalRepository;

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

    public RentalDTO updateRental(RentalDTO rentalDTO, Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with id " + id));
        dtoToRentalMapper.updateExisting(rentalDTO, rental);
        rentalRepository.save(rental);
        return rentalDTO;
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

    // Here we use cloudinary to upload file to cloud storage and return url
    public String storeFile(MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            @SuppressWarnings("unchecked") // because of the cloudinary upload method which returns a raw map
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    // The convertMultiPartToFile method converts a MultipartFile object to a File object,
    // creates a new File object with the original name of the downloaded file,
    // writes the bytes of the downloaded file to the new File object and returns it.
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
