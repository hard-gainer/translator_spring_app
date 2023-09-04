package com.pet_project.translator.mappers;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.WordDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest
public class DictionaryWordMapperTest {

    private final DictionaryWordMapper mapper = Mappers.getMapper(DictionaryWordMapper.class);

//    @Test
//    public void testMapper() {
//
//        Map<UUID, Word> map = new HashMap<>();
//        map.put(randomUUID(), Word.builder()..build())
//
//        Dictionary dictionary = Dictionary.builder()
//                .name("Test_Dictionary")
//                .wordMap()
//                .build();
//
//        Word word = Word.builder()
//                .id(randomUUID())
//                .text("TER")
//                .translation("реп")
//                .dictionary()
//                .build();
//
//
//
//        word.setDictionary(Dictionary.builder()
//                                     .wordMap(new HashMap<>())
//                                     .build());
//        WordDTO dto = mapper.wordToWordDto(word);
//        assertEquals(word.getDictionary().getWordMap(), dto.getDictionary().getWordMap());
//    }

}
