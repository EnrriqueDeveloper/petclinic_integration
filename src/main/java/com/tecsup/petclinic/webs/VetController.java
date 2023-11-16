package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling Vet-related HTTP requests.
 * Handles CRUD operations for Vet entities.
 *
 * @author jgomezm
 */
@RestController
@Slf4j
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;
    private final VetMapper vetMapper;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
        this.vetMapper = vetMapper;
    }

    /**
     * Get all vets.
     *
     * @return A list of VetTO objects.
     */
    @GetMapping
    public ResponseEntity<List<VetTO>> findAllVets() {
        List<Vet> vets = vetService.findAll();
        log.info("vets: {}", vets);

        List<VetTO> vetsTO = vetMapper.toVetTOList(vets);
        log.info("vetsTO: {}", vetsTO);

        return ResponseEntity.ok(vetsTO);
    }

    /**
     * Create a new vet.
     *
     * @param vetTO The VetTO object containing the information for the new vet.
     * @return The created VetTO.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetTO> create(@RequestBody VetTO vetTO) {
        Vet newVet = vetMapper.toVet(vetTO);
        VetTO newVetTO = vetMapper.toVetTO(vetService.create(newVet));

        return ResponseEntity.status(HttpStatus.CREATED).body(newVetTO);
    }

    /**
     * Find a vet by ID.
     *
     * @param id The ID of the vet to be found.
     * @return The found VetTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VetTO> findById(@PathVariable Integer id) {
        VetTO vetTO;

        try {
            Vet vet = vetService.findById(id);
            vetTO = vetMapper.toVetTO(vet);

        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vetTO);
    }

    /**
     * Update and create a vet.
     *
     * @param vetTO The VetTO object containing the updated information.
     * @param id    The ID of the vet to be updated.
     * @return The updated VetTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VetTO> update(@RequestBody VetTO vetTO, @PathVariable Integer id) {
        VetTO updateVetTO;

        try {
            Vet updateVet = vetService.findById(id);
            updateVet.setFirstName(vetTO.getFirstName());
            updateVet.setLastName(vetTO.getLastName());

            vetService.update(updateVet);
            updateVetTO = vetMapper.toVetTO(updateVet);

        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updateVetTO);
    }

    /**
     * Delete a vet by ID.
     *
     * @param id The ID of the vet to be deleted.
     * @return A ResponseEntity indicating success or failure.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
