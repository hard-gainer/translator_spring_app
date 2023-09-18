package com.pet_project.translator.services;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;
import jakarta.validation.Valid;

import java.util.*;

public interface DictionaryService {

    Word saveNewWord(UUID dictionaryId, WordDTO word);

    Optional<List<WordDTO>> getDictionaryById(UUID id, Integer pageNumber, Integer pageSize);

    Map<UUID, String> printAllDictionaries();

    Optional<WordDTO> updateWordById(UUID dictionaryId, WordDTO word);

    Boolean deleteWordById(UUID dictionaryId, UUID wordId);

    void deleteDictionaryById(UUID dictionaryId);

    DictionaryDTO saveNewDictionary(DictionaryDTO dictionary);
}
