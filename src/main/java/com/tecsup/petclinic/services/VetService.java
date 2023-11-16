package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;

/**
 * Service interface for Vet entities.
 * Define operations related to Vet entities.
 *
 * @author jgomezm
 */
public interface VetService {

    /**
     * Create a new Vet.
     *
     * @param vet The Vet to be created.
     * @return The created Vet.
     */
    Vet create(Vet vet);

    /**
     * Update an existing Vet.
     *
     * @param vet The Vet to be updated.
     * @return The updated Vet.
     */
    Vet update(Vet vet);

    /**
     * Delete a Vet by ID.
     *
     * @param id The ID of the Vet to be deleted.
     * @throws VetNotFoundException If the Vet with the given ID is not found.
     */
    void delete(Integer id) throws VetNotFoundException;

    /**
     * Find a Vet by ID.
     *
     * @param id The ID of the Vet to be found.
     * @return The found Vet.
     * @throws VetNotFoundException If the Vet with the given ID is not found.
     */
    Vet findById(Integer id) throws VetNotFoundException;

    /**
     * Find Vets by their first name.
     *
     * @param firstName The first name to search for.
     * @return A list of Vets with the given first name.
     */
    List<Vet> findByFirstName(String firstName);

    /**
     * Find Vets by their last name.
     *
     * @param lastName The last name to search for.
     * @return A list of Vets with the given last name.
     */
    List<Vet> findByLastName(String lastName);

    /**
     * Find all Vets.
     *
     * @return A list of all Vets.
     */
    List<Vet> findAll();
}
