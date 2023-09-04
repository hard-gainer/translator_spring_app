package com.pet_project.translator.controller;

import com.pet_project.translator.services.DictionaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DictionaryController.class)
class DictionaryControllerTest {

    @MockBean
    DictionaryService dictionaryService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getDictionaryByIdNotFound() throws Exception {
        given(dictionaryService.getDictionaryById(any(UUID.class), 0, 25)).willThrow(NotFoundException.class);

        mockMvc.perform(get("http://localhost:8080/api/v1/dictionary/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }

}