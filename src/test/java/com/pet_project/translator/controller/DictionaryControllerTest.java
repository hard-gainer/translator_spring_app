package com.pet_project.translator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.TYPE;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.services.DictionaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DictionaryController.class)
class DictionaryControllerTest {

    @MockBean
    private DictionaryService dictionaryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void deleteDictionaryById() throws Exception {
        given(dictionaryService.deleteDictionaryById(any())).willReturn(Boolean.TRUE);

        mockMvc.perform(delete(DictionaryController.START + DictionaryController.DICTIONARY_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDictionaryByIdNotFound() throws Exception {
        given(dictionaryService.deleteDictionaryById(any())).willReturn(Boolean.FALSE);

        mockMvc.perform(delete(DictionaryController.START + DictionaryController.DICTIONARY_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWordById() throws Exception {
        given(dictionaryService.deleteWordById(any(), any())).willReturn(Optional.empty());

        mockMvc.perform(delete(DictionaryController.START + DictionaryController.WORD_PATH_ID, UUID.randomUUID(), UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteWordByIdNotFound() throws Exception {
        given(dictionaryService.deleteWordById(any(), any())).willReturn(Optional.of(TYPE.WORD));

        mockMvc.perform(delete(DictionaryController.START + DictionaryController.WORD_PATH_ID, UUID.randomUUID(), UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveNewDictionary() throws Exception {

        DictionaryDTO testDictionaryDTO = DictionaryDTO.builder()
                .id(UUID.randomUUID())
                .name("Test_Dict")
                .wordMap(new HashMap<>())
                .number_of_rows(0)
                .build();

        given(dictionaryService.saveNewDictionary(any(DictionaryDTO.class))).willReturn(testDictionaryDTO);

        mockMvc.perform(post(DictionaryController.START + DictionaryController.DICTIONARY_PATH, testDictionaryDTO)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDictionaryDTO)));
    }

    @Test
    void saveNewWord() throws Exception {

        WordDTO testWordDTO = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("car")
                .translation("машина")
                .build();

        given(dictionaryService.saveNewWord(any(), any(WordDTO.class))).willReturn(testWordDTO);

        mockMvc.perform(post(DictionaryController.START + DictionaryController.DICTIONARY_PATH_ID, UUID.randomUUID(), testWordDTO)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testWordDTO)));
    }

    @Test
    void printAllDictionaries() throws Exception {

        given(dictionaryService.printAllDictionaries(anyInt(), anyInt())).willReturn(Page.empty());

        mockMvc.perform(get(DictionaryController.START + DictionaryController.DICTIONARY_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // тест для проверки if'a, который выбрасывает NotFoundException
    @Test
    void getDictionaryByIdNotFound() throws Exception {
        given(dictionaryService.getDictionaryById(any(UUID.class), any(), any())).willReturn(Page.empty());

        mockMvc.perform(get(DictionaryController.START + DictionaryController.DICTIONARY_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDictionaryById() throws Exception {

        Page<WordDTO> testList = new PageImpl<>(List.of(WordDTO.builder().build()));
        given(dictionaryService.getDictionaryById(any(UUID.class), any(), any())).willReturn(testList);

        mockMvc.perform(get(DictionaryController.START + DictionaryController.DICTIONARY_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testList)))
                .andExpect(status().isOk());
    }
}