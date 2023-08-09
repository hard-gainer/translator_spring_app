package com.pet_project.translator.services;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private Map<UUID, DictionaryDTO> dictionaryMap;

    public DictionaryServiceImpl() {
        this.dictionaryMap = new HashMap<>();

        WordDTO word1 = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("1")
                .build();


        WordDTO word2 = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("2")
                .build();

        WordDTO word3 = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("3")
                .build();

        WordDTO word4 = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("4")
                .build();

        WordDTO word5 = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("5")
                .build();

        WordDTO word6 = WordDTO.builder()
                .id(UUID.randomUUID())
                .text("6")
                .build();

        List<WordDTO> wordList1 = Arrays.asList(word1, word2, word3);
        List<WordDTO> wordList2 = Arrays.asList(word4, word5, word6);

        DictionaryDTO dictionary1 = DictionaryDTO.builder()
                .id(UUID.randomUUID())
                .name("Dictionary_1")
                .wordMap(wordList1.stream().collect(Collectors.toMap(WordDTO::getId, word -> word)))
                .build();

        DictionaryDTO dictionary2 = DictionaryDTO.builder()
                .id(UUID.randomUUID())
                .name("Dictionary_2")
                .wordMap(wordList2.stream().collect(Collectors.toMap(WordDTO::getId, word -> word)))
                .build();

        dictionaryMap.put(dictionary1.getId(), dictionary1);
        dictionaryMap.put(dictionary2.getId(), dictionary2);
    }

    @Override
    public void saveNewWord(UUID dictionaryId, WordDTO word) {

        DictionaryDTO dictionary = dictionaryMap.get(dictionaryId);

        WordDTO newWord = WordDTO.builder()
                .id(UUID.randomUUID())
                .text(word.getText())
                .build();


        dictionary.getWordMap().put(newWord.getId(), newWord);
    }

    @Override
    public List<WordDTO> getDictionaryById(UUID dictionaryId) {
        return new ArrayList<>(dictionaryMap.get(dictionaryId).getWordMap().values());
    }

    @Override
    public Map<UUID, String> printAllDictionaries() {

        List<DictionaryDTO> values = new ArrayList<>(dictionaryMap.values());
        return new HashMap<>(values.stream().collect(Collectors.toMap(DictionaryDTO::getId, DictionaryDTO::getName)));
    }

    @Override
    public void updateWordById(UUID dictionaryId, WordDTO word) {
        DictionaryDTO dictionary = dictionaryMap.get(dictionaryId);
        WordDTO existing_word = dictionary.getWordMap().get(word.getId());
        existing_word.setText(word.getText());
    }

    @Override
    public void deleteWordById(UUID dictionaryId, UUID wordId) {
        DictionaryDTO dictionary = dictionaryMap.get(dictionaryId);
        dictionary.getWordMap().remove(wordId);
    }

    @Override
    public void deleteDictionaryById(UUID dictionaryId) {
        dictionaryMap.remove(dictionaryId);
    }

    @Override
    public void saveNewDictionary(DictionaryDTO dictionary) {
        String dictionaryName = dictionary.getName();
        dictionary = DictionaryDTO.builder()
                .id(UUID.randomUUID())
                .name(dictionaryName)
                .rows(0)
                .wordMap(new HashMap<>())
                .build();

        dictionaryMap.put(dictionary.getId(), dictionary);
    }

}
