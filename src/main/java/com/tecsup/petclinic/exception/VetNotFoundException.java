package com.tecsup.petclinic.exception;

/**
 * Custom exception class for Vet not found.
 * Extend this class to create exceptions related to Vet entities.
 *
 * @author jgomezm
 */
public class VetNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public VetNotFoundException(String message) {
        super(message);
    }

}
