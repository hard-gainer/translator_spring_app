package com.pet_project.translator.services;

import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.TYPE;
import com.pet_project.translator.models.WordDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface DictionaryService {

    WordDTO saveNewWord(UUID dictionaryId, WordDTO word);

    Page<WordDTO> getDictionaryById(UUID id, Integer pageNumber, Integer pageSize);

    Page<DictionaryDTO> printAllDictionaries(Integer pageNumber, Integer pageSize);

    Optional<WordDTO> updateWordById(UUID dictionaryId, WordDTO word);

    Optional<TYPE> deleteWordById(UUID dictionaryId, UUID wordId);

    Boolean deleteDictionaryById(UUID dictionaryId);

    DictionaryDTO saveNewDictionary(DictionaryDTO dictionary);
}
