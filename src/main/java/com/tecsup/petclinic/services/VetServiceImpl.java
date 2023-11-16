package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;

/**
 * Service implementation for managing Vet entities.
 *
 * @author jgomezm
 */
@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    /**
     * Create a new vet.
     *
     * @param vet The vet to be created.
     * @return The created vet.
     */
    @Override
    public Vet create(Vet vet) {
        return vetRepository.save(vet);
    }

    /**
     * Update an existing vet.
     *
     * @param vet The vet to be updated.
     * @return The updated vet.
     */
    @Override
    public Vet update(Vet vet) {
        return vetRepository.save(vet);
    }

    /**
     * Delete a vet by its ID.
     *
     * @param id The ID of the vet to be deleted.
     * @throws VetNotFoundException If the vet with the given ID is not found.
     */
    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Vet vet = findById(id);
        vetRepository.delete(vet);
    }

    /**
     * Find a vet by its ID.
     *
     * @param id The ID of the vet to be found.
     * @return The found vet.
     * @throws VetNotFoundException If the vet with the given ID is not found.
     */
    @Override
    public Vet findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);

        if (!vet.isPresent()) {
            throw new VetNotFoundException("Vet not found with ID: " + id);
        }

        return vet.get();
    }

    /**
     * Find vets by their first name.
     *
     * @param firstName The first name of the vets to be found.
     * @return The list of vets with the given first name.
     */
    @Override
    public List<Vet> findByFirstName(String firstName) {
        List<Vet> vets = vetRepository.findByFirstName(firstName);

        vets.forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     * Find vets by their last name.
     *
     * @param lastName The last name of the vets to be found.
     * @return The list of vets with the given last name.
     */
    @Override
    public List<Vet> findByLastName(String lastName) {
        List<Vet> vets = vetRepository.findByLastName(lastName);

        vets.forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     * Find all vets.
     *
     * @return The list of all vets.
     */
    @Override
    public List<Vet> findAll() {
        return vetRepository.findAll();
    }
}