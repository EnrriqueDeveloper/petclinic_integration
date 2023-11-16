package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.VetTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for VetController
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVets() throws Exception {

        // Implementa la lógica para obtener la cantidad esperada de registros de vet y el primer ID.
        // int NRO_RECORD = ...;
        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindVetOK() throws Exception {

        String FIRST_NAME_VET = "James";
        String LAST_NAME_VET = "Carter";

        mockMvc.perform(get("/vets/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME_VET)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME_VET)));
    }

    @Test
    public void testFindVetKO() throws Exception {

        String NAME_PET = "Leo";
        int TYPE_ID = 1;
        int OWNER_ID = 1;
        String BIRTH_DATE = "2000-09-07";

        mockMvc.perform(get("/pets/1"))  // Object must be BASIL
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(NAME_PET)))
                .andExpect(jsonPath("$.typeId", is(TYPE_ID)))
                .andExpect(jsonPath("$.ownerId", is(OWNER_ID)))
                .andExpect(jsonPath("$.birthDate", is(BIRTH_DATE)));
    }

    @Test
    public void testCreateVet() throws Exception {

        String FIRST_NAME_VET = "John";
        String LAST_NAME_VET = "Doe";

        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(FIRST_NAME_VET);
        newVetTO.setLastName(LAST_NAME_VET);

        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME_VET)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME_VET)));
    }

    @Test
    public void testDeleteVet() throws Exception {

        String FIRST_NAME_VET = "John";
        String LAST_NAME_VET = "Doe";

        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(FIRST_NAME_VET);
        newVetTO.setLastName(LAST_NAME_VET);

        ResultActions mvcActions = mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();

        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }
}
