package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testCreateVet() {
        String first_name = "Joe";
        String last_name = "Suarez";
        Vet vet = new Vet (first_name, last_name);

        Vet vet_created = this.vetService.create(vet);
        log.info("VET CREATED :" + vet_created.toString());
        assertNotNull(vet_created);
        assertEquals(first_name, vet_created.getFirstName());
        assertEquals(last_name, vet_created.getLastName());
    }
    @Test
    public void testReadVet() {
        // Crear un veterinario para probar la lectura
        String first_name = "John";
        String last_name = "Doe";
        Vet vetToRead = new Vet(first_name, last_name);
        Vet createdVet = this.vetService.create(vetToRead);

        // Realizar la lectura utilizando el servicio
        try {
            Vet readVet = this.vetService.findById(createdVet.getId());

            assertNotNull(readVet);

            assertEquals(first_name, readVet.getFirstName());
            assertEquals(last_name, readVet.getLastName());
        } catch (VetNotFoundException e) {
            fail("No se esperaba VetNotFoundException al leer el veterinario");
        }
    }
    @Test
    public void testUpdateVet() {

        String VET_FIRST_NAME = "James";
        String VET_LAST_NAME = "Carter";

        String UP_VET_FIRST_NAME = "Antonio";
        String UP_VET_LAST_NAME = "Guzman";

        Vet vet = new Vet(VET_FIRST_NAME, VET_LAST_NAME);
        // ------------ Create ---------------
        log.info("Creating vet: {}", vet);
        Vet vetCreated = this.vetService.create(vet);
        log.info("Vet created: {}", vetCreated);
        // ------------ Update ---------------
        // Prepare data for update
        vetCreated.setFirstName(UP_VET_FIRST_NAME);
        vetCreated.setLastName(UP_VET_LAST_NAME);
        // Actualiza otros atributos si es necesario
        // ...
        // Execute update
        Vet updatedVet = this.vetService.update(vetCreated);
        log.info("Vet updated: {}", updatedVet);


        // ------------ Validation ---------------

        // EXPECTED vs. ACTUAL
        assertEquals(UP_VET_FIRST_NAME, updatedVet.getFirstName());
        assertEquals(UP_VET_LAST_NAME, updatedVet.getLastName());
        // Verifica otros atributos actualizados
        // ...

    }
    @Test
    public void testDeleteVet() {

        String FIRST_NAME = "James";
        String LAST_NAME = "Carter";

        // ------------ Crear Veterinario ---------------
        Vet vet = new Vet(FIRST_NAME, LAST_NAME);
        vet = this.vetService.create(vet);
        log.info("Veterinario creado: " + vet);

        // ------------ Eliminar Veterinario ---------------
        try {
            this.vetService.delete(vet.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validar Eliminación ---------------
        try {
            this.vetService.findById(vet.getId());
            assertTrue(false, "Se esperaba VetNotFoundException");
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }

    }
}