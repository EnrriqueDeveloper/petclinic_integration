package com.tecsup.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Vet;

import java.util.List;

/**
 * Repository interface for Vet entities.
 * Extends CrudRepository to inherit basic CRUD operations.
 *
 * @author jgomezm
 */
@Repository
public interface VetRepository extends CrudRepository<Vet, Integer> {


    List<Vet> findByFirstName(String firstName);


    List<Vet> findByLastName(String lastName);


    //List<Vet> findBySpecialty(String specialty);

    @Override
    List<Vet> findAll();
}
