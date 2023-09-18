package com.pet_project.translator.repositories;

import com.pet_project.translator.TranslatorApplication;
import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.services.DictionaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DictionaryRepositoryTest {

    @MockBean
    DictionaryService dictionaryService;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    WordRepository wordRepository;


    private static Logger logger = LoggerFactory.getLogger(DictionaryRepositoryTest.class);

//    @BeforeEach
//    @Rollback
//    void setUp() {
//        dictionaryRepository.saveAll(List.of(Dictionary.builder().name("Dictionary_1").id(UUID.randomUUID()).build(),
//                Dictionary.builder().name("Dictionary_2").id(UUID.randomUUID()).build()));
//    }

    @Test
    void testDictionaryRepository() {
        List<Dictionary> d_list = dictionaryRepository.findAll();
        assertThat(d_list).hasSize(1);
    }

    @Test
    void testWordRepository() {
        List<Word> w_list = wordRepository.findAll();
        assertThat(w_list).hasSize(52);
    }


    @Rollback
    @Test
    void deleteDictionaryById() {

        List<Word> w_list = wordRepository.findAll();
        assertThat(w_list).hasSize(100);

//        dictionaryService.deleteWordById(UUID.fromString("75da16b1-961e-4dfb-b14f-cfe57136da2d"), UUID.fromString("20993dfd-efac-42ed-9c58-5bee3c07206a"));
//
//        List<Word> w_list1 = wordRepository.findAll();
//        assertThat(w_list1).hasSize(102);
    }


}