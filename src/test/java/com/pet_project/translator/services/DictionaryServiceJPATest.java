package com.pet_project.translator.services;

import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.repositories.DictionaryRepository;
import com.pet_project.translator.repositories.WordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest
class DictionaryServiceJPATest {

    @MockBean
    DictionaryService dictionaryService;

    @Autowired
    MockMvc mockMvc;

    DictionaryRepository dictionaryRepository;

    WordRepository wordRepository;


//    private final UUID dictionaryId = UUID.fromString("4ad25e0f-6701-4f92-9fd1-dcefc65f1161");

//    private final UUID dictionaryId = dictionaryService.printAllDictionaries().keySet().stream().toList().get(0);
    private final WordDTO word = WordDTO.builder().text("test").translation("test").build();

//    @Test
//    void getDictionaryById() throws Exception {
//
////        mockMvc.perform(get("http://localhost:8080/api/v1/dictionary/4ad25e0f-6701-4f92-9fd1-dcefc65f1161")
////                        .accept(MediaType.APPLICATION_JSON))
////                        .andExpect(status().isOk())
////                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                        .andExpect(jsonPath("$.content.length()", is(3)));
//
//
//
//        given(dictionaryService.getDictionaryById(dictionaryId, 6, 10)).willReturn(Optional.of(List.of()));
//    }

    //нужно проверить, что новое слово сохраняется в репозитории WordRepository

    @Rollback
    @Test
    void saveNewWord() {

//        Word savedWord = dictionaryService.saveNewWord(dictionaryId, word);
//        assertEquals(savedWord.getText(), word.getText());
//        assertEquals(savedWord.getTranslation(), word.getTranslation());

        UUID dictionaryId = dictionaryService.printAllDictionaries().keySet().stream().toList().get(0);

        dictionaryService.getDictionaryById(dictionaryId, 0, 10).get().get(0);


    }
}