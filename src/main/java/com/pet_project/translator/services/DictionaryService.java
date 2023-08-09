package com.pet_project.translator.services;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;

import java.util.*;

public interface DictionaryService {

    void saveNewWord(UUID dictionaryId, WordDTO word);
    List<WordDTO> getDictionaryById(UUID id);
    Map<UUID, String> printAllDictionaries();
    void updateWordById(UUID dictionaryId, WordDTO word);
    void deleteWordById(UUID dictionaryId, UUID wordId);
    void deleteDictionaryById(UUID dictionaryId);
    void saveNewDictionary(DictionaryDTO dictionary);
}
